package com.arnoract.projectx.core.api.model.user

data class NetworkUser(
    val userId: String? = null,
    val profileUrl: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val coin: Int? = null
)
