package com.arnoract.projectx.ui.lesson.ui

sealed class ConfirmPurchaseState {
    object Hide : ConfirmPurchaseState()
    data class Show(val data: ConfirmPurchaseModel) : ConfirmPurchaseState()
}

data class ConfirmPurchaseModel(
    val id: String,
    val titleTh: String,
    val coin: String
)
