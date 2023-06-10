package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ReadingArticle
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): List<Article>

    suspend fun getArticleById(id: String): Article

    suspend fun getArticleByCategoryId(categoryId: String): List<Article>

    suspend fun getCurrentParagraphFromDbUseCase(articleId: String): Int

    suspend fun setCurrentParagraphToDb(id: String, progress: Int)

    fun observeReadingArticles(): Flow<List<ReadingArticle>>

    suspend fun getReadingArticles(): List<ReadingArticle>

    suspend fun createReadingArticleToDb(model: Article)

    suspend fun createReadingArticleFromReadingArticleToDb(models: List<ReadingArticle>)

    suspend fun removeReadingArticleById(id: String)

    suspend fun setReaderFontSizeSetting(value: Int)

    suspend fun getReaderFontSuzeSetting(): Int

    suspend fun setReaderBackgroundModeSetting(value: Int)

    suspend fun getReaderBackgroundModeSetting(): Int
}