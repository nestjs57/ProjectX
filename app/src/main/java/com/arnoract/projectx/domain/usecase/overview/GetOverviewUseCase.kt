package com.arnoract.projectx.domain.usecase.overview

import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.UserRepository
import com.google.gson.reflect.TypeToken

class GetOverviewUseCase(
    private val userRepository: UserRepository,
) : UseCase<Unit, List<ReadingArticle>>() {
    override suspend fun execute(parameters: Unit): List<ReadingArticle> {
        val currentUser = userRepository.getUser()
        return currentUser.readingRawState.toArrayClass()
    }

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = MyGsonBuilder().build()
        val type = object : TypeToken<ArrayList<ReadingArticle>>() {}.type
        return gson.fromJson(this, type)
    }
}