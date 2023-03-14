package com.arnoract.projectx.domain.model.article

data class ReadingArticle(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val descriptionTh: String,
    val descriptionEn: String,
    val currentParagraph: Int = 0,
    val totalParagraph: Int,
    val category: ArticleCategory?,
    val imageUrl: String,
)
