package com.arnoract.projectx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    fun initBilling() {
        viewModelScope.launch {
            val result = getIsSubscription()
            print("")
        }
    }
}