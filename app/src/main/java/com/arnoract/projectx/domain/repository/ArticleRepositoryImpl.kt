package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.article.NetworkArticle
import com.arnoract.projectx.core.api.model.article.mapper.ArticleEntityToArticleMapper
import com.arnoract.projectx.core.api.model.article.mapper.ArticleToArticleEntityMapper
import com.arnoract.projectx.core.api.model.article.mapper.NetworkArticleToArticleMapper
import com.arnoract.projectx.core.db.dao.ArticleDao
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.pref.ReaderPreferenceStorage
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ArticleRepositoryImpl(
    private val db: FirebaseFirestore,
    private val articleDao: ArticleDao,
    private val readerPreferenceStorage: ReaderPreferenceStorage
) :
    ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        val result = db.collection("articles").whereEqualTo("isPublic", true).get().await()
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
        return model.copy(viewCount = model.viewCount.plus(1))
    }

    override suspend fun getArticleByCategoryId(categoryId: String): List<Article> {
        val result = db.collection("articles").whereEqualTo("isPublic", true).get().await()
        return result.documents.map {
            it.toObject<NetworkArticle>()?.copy(id = it.id)
        }.filter {
            if (categoryId.toInt() == 0) {
                true
            } else {
                it?.category == categoryId.toInt() && it.isPublic == true
            }
        }.map {
            NetworkArticleToArticleMapper.map(it)
        }
    }

    override suspend fun getCurrentParagraphFromDbUseCase(articleId: String): Int {
        val entity = articleDao.findById(articleId)
        return entity?.currentParagraph ?: 0
    }

    override suspend fun setCurrentParagraphToDb(id: String, progress: Int) {
        articleDao.updateCurrentParagraphById(id, progress)
    }

    override fun observeReadingArticles(): Flow<List<ReadingArticle>> {
        val data = articleDao.observeFindAll()
        return data.map {
            it.map { entity ->
                ArticleEntityToArticleMapper.map(entity)
            }
        }
    }

    override suspend fun createReadingArticleToDb(model: Article) {
        val entity = articleDao.findById(model.id ?: "")
        if (entity == null) {
            articleDao.insert(ArticleToArticleEntityMapper(progress = 0).map(model))
        } else {
            articleDao.update(
                ArticleToArticleEntityMapper(progress = entity.currentParagraph).map(
                    model
                )
            )
        }
    }

    override suspend fun setReaderFontSizeSetting(value: Int) {
        readerPreferenceStorage.settingFontSize = value
    }

    override suspend fun getReaderFontSuzeSetting(): Int {
        return readerPreferenceStorage.settingFontSize
    }

    override suspend fun setReaderBackgroundModeSetting(value: Int) {
        readerPreferenceStorage.settingBackgroundMode = value
    }

    override suspend fun getReaderBackgroundModeSetting(): Int {
        return readerPreferenceStorage.settingBackgroundMode
    }
}