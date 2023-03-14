package com.arnoract.projectx.ui.home.model

data class UiArticleHorizontalItem(
    val id: String,
    val imageUrl: String,
    val titleTh: String,
    val titleEn: String,
    val category: UiArticleCategory?,
    val descriptionTh: String,
    val descriptionEn: String,
    val viewCount: Int
)