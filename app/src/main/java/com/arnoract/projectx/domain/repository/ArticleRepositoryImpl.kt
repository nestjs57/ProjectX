package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.article.NetworkArticle
import com.arnoract.projectx.core.api.model.article.mapper.ArticleEntityToArticleMapper
import com.arnoract.projectx.core.api.model.article.mapper.ArticleToArticleEntityMapper
import com.arnoract.projectx.core.api.model.article.mapper.NetworkArticleToArticleMapper
import com.arnoract.projectx.core.db.dao.ArticleDao
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ArticleRepositoryImpl(private val db: FirebaseFirestore, private val articleDao: ArticleDao) :
    ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        val result = db.collection("articles").get().await()
        return result.documents.map {
            NetworkArticleToArticleMapper.map(it.toObject<NetworkArticle>()?.copy(id = it.id))
        }
    }

    override suspend fun getArticleById(id: String): Article {
        val result =
            db.collection("articles").whereEqualTo(FieldPath.documentId(), id).get().await()
        val model = NetworkArticleToArticleMapper.map(
            result.documents.firstOrNull()?.toObject<NetworkArticle>()?.copy(id = id)
        )
        db.collection("articles").document(id).update("viewCount", model.viewCount.plus(1))
        val entity = articleDao.findById(id)
        if (entity == null) {
            articleDao.insert(ArticleToArticleEntityMapper(progress = 0).map(model))
        }
        return model.copy(viewCount = model.viewCount.plus(1))
    }

    override suspend fun getArticleByCategoryId(categoryId: String): List<Article> {
        val result = db.collection("articles").get().await()
        return result.documents.map {
            it.toObject<NetworkArticle>()?.copy(id = it.id)
        }.filter { it?.category == categoryId.toInt() }.map {
            NetworkArticleToArticleMapper.map(it)
        }
    }

    override suspend fun getCurrentParagraphFromDbUseCase(articleId: String): Int {
        val entity = articleDao.findById(articleId)
        return entity?.currentParagraph ?: 0
    }

    override suspend fun setCurrentParagraphToDb(id: String, progress: Int) {
        articleDao.updateProgressById(id, progress)
    }

    override fun observeReadingArticles(): Flow<List<ReadingArticle>> {
        val data = articleDao.observeFindAll()
        return data.map {
            it.map { entity ->
                ArticleEntityToArticleMapper.map(entity)
            }
        }
    }
}