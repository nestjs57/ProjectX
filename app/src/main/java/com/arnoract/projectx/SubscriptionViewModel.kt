package com.arnoract.projectx

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.KoinConst
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface SubscriptionViewModelDelegate {
    val billingClient: LiveData<BillingClient>
    fun setBillingClient(billingClient: BillingClient)
    fun getBillingClient(): BillingClient?
    suspend fun getIsSubscription(): Boolean
}

class SubscriptionViewModelDelegateImpl(
    val context: Context,
) : SubscriptionViewModelDelegate {

    private val _billingClient = MutableLiveData<BillingClient>()
    override val billingClient: LiveData<BillingClient>
        get() = _billingClient

    override fun setBillingClient(billingClient: BillingClient) {
        _billingClient.value = billingClient
    }

    override fun getBillingClient(): BillingClient? {
        return _billingClient.value
    }

    override suspend fun getIsSubscription(): Boolean = suspendCoroutine { continuation ->
        _billingClient.value?.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {}

            override fun onBillingSetupFinished(p0: BillingResult) {
                val executeService = Executors.newSingleThreadExecutor()
                executeService.execute {
                    val paramsP = QueryPurchasesParams.newBuilder()
                        .setProductType(BillingClient.ProductType.SUBS)

                    billingClient.value?.queryPurchasesAsync(paramsP.build()) { _, list ->
                        if (list.isEmpty()) {
                            return@queryPurchasesAsync continuation.resume(false)
                        } else {
                            for (purchase in list) {
                                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                    return@queryPurchasesAsync continuation.resume(true)
                                } else {
                                    return@queryPurchasesAsync continuation.resume(false)
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}

class SubscriptionViewModel(
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _isSubscribed = MutableLiveData<Boolean>()
    val isSubscribed: LiveData<Boolean> get() = _isSubscribed

    private val _subscribedSuccess = MutableSharedFlow<Unit>()
    val subscribedSuccess: MutableSharedFlow<Unit> get() = _subscribedSuccess

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
                refreshIsSubscribed()
                viewModelScope.launch {
                    _subscribedSuccess.emit(Unit)
                }
            }
        }

    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            val base64Key = KoinConst.Key.keyLiCent
            val security = Security()
            security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }

    fun initBilling(context: Context) {
        setBillingClient(
            BillingClient.newBuilder(context).setListener(purchasesUpdatedListener)
                .enablePendingPurchases().build()
        )
    }

    fun refreshIsSubscribed() {
        viewModelScope.launch {
            val isPurchase = getIsSubscription()
            _isSubscribed.value = isPurchase
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        val consumeParam =
            ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        val listener = ConsumeResponseListener { billingResult, s ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

            }
        }
        getBillingClient()?.consumeAsync(consumeParam, listener)

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                //toast("Error : Invalid Purchase")
                return
            }
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)
                        .build()
                getBillingClient()?.acknowledgePurchase(
                    acknowledgePurchaseParams, acknowledgePurchaseResponseListener
                )
            } else {
                refreshIsSubscribed()
                viewModelScope.launch {
                    _subscribedSuccess.emit(Unit)
                }
            }
        } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
            //toast("subscribe PENDING")
        } else if (purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            //toast("subscribe UNSPECIFIED_STATE")
        } else {
            //toast("subscribe UNSPECIFIED_STATE")
        }
    }

    fun openPaywall(activity: ComponentActivity) {
        getBillingClient()?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                }

                val productList = listOf(
                    QueryProductDetailsParams.Product.newBuilder().setProductId("premium1")
                        .setProductType(BillingClient.ProductType.SUBS).build()
                )
                val params = QueryProductDetailsParams.newBuilder().setProductList(productList)

                getBillingClient()!!.queryProductDetailsAsync(params.build()) { billingResult, productDetailsList ->
                    for (productDetails in productDetailsList) {
                        val offerToken = productDetails.subscriptionOfferDetails?.get(0)?.offerToken
                        val productDetailParamsList = listOf(offerToken?.let {
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails).setOfferToken(it).build()
                        })
                        val billingFlowParams = BillingFlowParams.newBuilder()
                            .setProductDetailsParamsList(productDetailParamsList).build()

                        getBillingClient()?.launchBillingFlow(
                            activity, billingFlowParams
                        )
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    fun endConnection() {
        if (getBillingClient() != null) {
            getBillingClient()?.endConnection()
        }
    }
}