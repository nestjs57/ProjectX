package com.arnoract.projectx.domain.model.article

data class ArticleSet(
    val id: String,
    val articleSetIcon: String,
    val articleSetName: String,
    val articles: List<String>,
    val backgroundColor: String
)