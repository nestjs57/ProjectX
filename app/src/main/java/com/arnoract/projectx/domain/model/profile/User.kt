package com.arnoract.projectx.domain.model.profile

import java.util.*

data class User(
    val userId: String,
    val profileUrl: String,
    val email: String,
    val displayName: String,
    val coin: Int? = 0,
    val readingRawState: String,
    val createAt: Date
)
