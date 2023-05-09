package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository
import com.google.gson.reflect.TypeToken

class SyncDataReadingUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        val currentUser = userRepository.getUser()
        if (currentUser.readingRawState.isBlank()) return
        val resultList = currentUser.readingRawState.toArrayClass<ReadingArticle>()
        articleRepository.createReadingArticleFromReadingArticleToDb(resultList)
    }

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = MyGsonBuilder().build()
        val type = object : TypeToken<ArrayList<ReadingArticle>>() {}.type
        return gson.fromJson(this, type)
    }
}