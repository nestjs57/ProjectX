package com.arnoract.projectx.domain.model.article

import java.util.*

data class Article(
    val id: String?,
    val category: ArticleCategory?,
    val imageUrl: String,
    val titleTh: String,
    val titleEn: String,
    val descriptionTh: String,
    val descriptionEn: String,
    val isComingSoon: Boolean,
    val isPremium: Boolean,
    val isRecommend: Boolean,
    val publicDate: Date,
    val viewCount: Int,
    val paragraphs: List<List<Paragraph>>? = null
)

data class Paragraph(
    val paragraphNum: Int,
    val vocabulary: String,
    val translate: String,
    val note: String,
)
