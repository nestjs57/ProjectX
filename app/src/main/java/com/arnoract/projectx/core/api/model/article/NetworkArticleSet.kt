package com.arnoract.projectx.core.api.model.article

data class NetworkArticleSet(
    val id: String? = null,
    val articleSetIcon: String? = null,
    val articleSetName: String? = null,
    val articles: List<String>? = null,
    val backgroundColor: String? = null,
)