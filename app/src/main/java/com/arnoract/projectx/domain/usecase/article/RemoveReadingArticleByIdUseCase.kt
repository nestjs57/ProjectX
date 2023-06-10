package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository

class RemoveReadingArticleByIdUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(parameters: String) {
        val currentReadingFromDb = articleRepository.getReadingArticles()
        val resultList = currentReadingFromDb.filter { it.id != parameters }
        val readingRawState =
            MyGsonBuilder().build().toJson(resultList)
        articleRepository.removeReadingArticleById(parameters)
        userRepository.updateReadingRawState(readingRawState)
    }
}