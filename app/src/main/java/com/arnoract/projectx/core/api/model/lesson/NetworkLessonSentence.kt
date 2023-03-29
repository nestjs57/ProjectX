package com.arnoract.projectx.core.api.model.lesson

import com.google.firebase.firestore.PropertyName
import java.util.*

data class NetworkLessonSentence(
    val id: String? = null,
    val titleTh: String? = null,
    val titleEn: String? = null,
    val descriptionTh: String? = null,
    val descriptionEn: String? = null,

    @JvmField @PropertyName("isComingSoon") val isComingSoon: Boolean? = null,

    @JvmField @PropertyName("isPublic") val isPublic: Boolean? = null,
    val imageUrl: String? = null,
    val publicDate: Date? = null,
    val contentRawState: String? = null,
    val priceCoin: Int? = 0
)
