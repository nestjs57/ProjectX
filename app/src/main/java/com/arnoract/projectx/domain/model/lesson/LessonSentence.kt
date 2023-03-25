package com.arnoract.projectx.domain.model.lesson

import java.util.*

data class LessonSentence(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val descriptionTh: String,
    val descriptionEn: String,
    val isComingSoon: Boolean,
    val isPublic: Boolean,
    val imageUrl: String,
    val publicDate: Date,
    val sentences: List<Sentence>? = listOf()
)

data class Sentence(
    val sentence: String, val translate: String
)
