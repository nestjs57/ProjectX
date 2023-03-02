package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.domain.model.article.Article

interface ArticleRepository {
    suspend fun getArticles(): List<Article>
    suspend fun getArticleById(id: String): Article
    suspend fun getArticleByCategoryId(categoryId: String): List<Article>
}