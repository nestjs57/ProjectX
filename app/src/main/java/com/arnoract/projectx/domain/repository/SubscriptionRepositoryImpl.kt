package com.arnoract.projectx.domain.repository

import android.content.Context
import com.android.billingclient.api.*
import com.arnoract.projectx.Security
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SubscriptionRepositoryImpl(private val androidContext: Context) : SubscriptionRepository {

    private var billingClient: BillingClient? = null

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            //toast("ITEM_ALREADY_OWNED")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
            //toast("FEATURE_NOT_SUPPORTED")
        } else {
            //toast(billingResult.debugMessage)
        }
    }

    private var acknowledgePurchaseResponseListener =
        AcknowledgePurchaseResponseListener { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                //toast("subscribed")
            }
        }

    override suspend fun initBillClient() {
        billingClient =
            BillingClient.newBuilder(androidContext).enablePendingPurchases()
                .setListener(purchasesUpdatedListener).build()
        billingClient?.connectOrThrow()
    }

    override suspend fun openPaywall() {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("premium1")
                .setProductType(BillingClient.ProductType.SUBS).build()
        )
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)

        val result = billingClient?.queryProductDetails(params.build())
    }


    private suspend fun BillingClient.connectOrThrow(): Boolean = suspendCoroutine { cont ->
        if (isReady) {
            return@suspendCoroutine cont.resume(true)
        }
        return@suspendCoroutine startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    cont.resume(true)
                } else {
                    cont.resume(false)
                }
            }

            override fun onBillingServiceDisconnected() {
                cont.resume(false)
            }
        })
    }

    private fun handlePurchase(purchase: Purchase) {
        val consumeParam =
            ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        val listener = ConsumeResponseListener { billingResult, s ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

            }
        }
        billingClient?.consumeAsync(consumeParam, listener)

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                //toast("Error : Invalid Purchase")
                return
            }
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)
                        .build()
                billingClient?.acknowledgePurchase(
                    acknowledgePurchaseParams, acknowledgePurchaseResponseListener
                )
            } else {
                //toast("already subscribed")
            }
        } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
            //toast("subscribe PENDING")
        } else if (purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            //toast("subscribe UNSPECIFIED_STATE")
        } else {
            //toast("subscribe UNSPECIFIED_STATE")
        }
    }

    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            val base64Key =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxE/NFVNRURzETpy09qW3d0JqklR/EjD58IZQg/6HszGvHi/PUP8jBykFTWMupKwSAutxfUZBN2bHM2DN2PEAWYvxdMM4p/44R7iAiC4tL8OU7eLiJKe5rL7nv480uF7uD01rTU5WDtZysyUVtPEf8pz9UIfcSDVrnlQPzcV5QiCuXgRHzrk30SeyBN0Z6kQakkMthST1h+76vXXjsS+8tZBVuOwZkAkqZFOVajxm0HUelRbQgPwkTw5jQoqoXYY3vckZwD0TpaoS3kmRDvW7kf1UeIumxAKRJ+mrvEqiPPqckUj5+iYCGUZT6KFxl2ahyvAvA0t9trlNvO0DMKlmdwIDAQAB"
            val security = Security()
            security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }
}