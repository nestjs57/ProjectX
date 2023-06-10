package com.arnoract.projectx.ui.home.model

data class UiArticleVerticalItem(
    val id: String,
    val imageUrl: String,
    val titleTh: String,
    val titleEn: String,
    val category: UiArticleCategory?,
    val progress: String? = null,
    val isBlock: Boolean = false,
    val isPremium: Boolean = false,
    val isEnablePremium: Boolean = false,
    val isShowColonSetting: Boolean = false
)
