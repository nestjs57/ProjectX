package com.arnoract.projectx.core.api.model.article

import com.google.firebase.firestore.PropertyName
import java.util.*

data class NetworkArticle(
    val id: String? = null,
    val imageUrl: String? = null,
    val titleTh: String? = null,
    val titleEn: String? = null,
    val descriptionTh: String? = null,
    val descriptionEn: String? = null,
    @JvmField @PropertyName("isRecommend")
    val isRecommend: Boolean? = null,
    @JvmField @PropertyName("isPremium")
    val isPremium: Boolean? = null,
    val category: Int? = null,
    val contentRawState: String? = null,
    @JvmField @PropertyName("isComingSoon")
    val isComingSoon: Boolean? = null,
    val publicDate: Date? = null,
    val viewCount: Int? = null
)
