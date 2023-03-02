package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.article.NetworkArticle
import com.arnoract.projectx.core.api.model.article.mapper.NetworkArticleToArticleMapper
import com.arnoract.projectx.domain.model.article.Article
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class ArticleRepositoryImpl(private val db: FirebaseFirestore) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        val result = db.collection("articles").get().await()
        return result.documents.map {
            NetworkArticleToArticleMapper.map(it.toObject<NetworkArticle>()?.copy(id = it.id))
        }
    }

    override suspend fun getArticleById(id: String): Article {
        val result =
            db.collection("articles").whereEqualTo(FieldPath.documentId(), id).get().await()
        return NetworkArticleToArticleMapper.map(
            result.documents.firstOrNull()?.toObject<NetworkArticle>()?.copy(id = id)
        )
    }

    override suspend fun getArticleByCategoryId(categoryId: String): List<Article> {
        val result = db.collection("articles").get().await()
        return result.documents.map {
            it.toObject<NetworkArticle>()?.copy(id = it.id)
        }.filter { it?.category == categoryId.toInt() }.map {
            NetworkArticleToArticleMapper.map(it)
        }
    }
}