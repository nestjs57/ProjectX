package com.arnoract.projectx.domain.repository

interface SubscriptionRepository {

    suspend fun initBillClient()

    suspend fun openPaywall()
}