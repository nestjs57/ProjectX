package com.arnoract.projectx.core.api.model.user

import java.util.*

data class NetworkUser(
    val userId: String? = null,
    val profileUrl: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val coin: Int? = null,
    val readingRawState: String? = null,
    val createAt: Date? = Date()
)
