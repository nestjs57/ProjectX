package com.arnoract.projectx.ui.lesson.model

import java.util.*

data class UiLessonSentence(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val descriptionTh: String,
    val descriptionEn: String,
    val isComingSoon: Boolean,
    val isPublic: Boolean,
    val imageUrl: String,
    val publicDate: Date,
    val sentences: List<UiSentence>? = listOf(),
    val priceCoin: String
)

data class UiSentence(
    val sentence: String,
    val translate: String
)
