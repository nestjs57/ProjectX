package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SyncDataReadingUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        val currentUser = userRepository.getUser()
        val currentReadingFromDb = articleRepository.getReadingArticles()

        if (currentUser.readingRawState.isBlank()) {
            val readingRawState = Gson().toJson(currentReadingFromDb)
            userRepository.updateReadingRawState(readingRawState)
        } else {
            val networkReading = currentUser.readingRawState.toArrayClass<ReadingArticle>()

            val resultMap = mutableMapOf<String, ReadingArticle>()

            for (progressData in networkReading + currentReadingFromDb) {
                val id = progressData.id
                val existingProgressData = resultMap[id]
                val selectedProgressData =
                    if (existingProgressData == null || progressData.currentParagraph > existingProgressData.currentParagraph) { // เช็คว่ายังไม่มี ProgressData ใน resultMap หรือ ProgressData ใหม่มีค่า progress มากกว่า ProgressData เดิม
                        progressData
                    } else {
                        existingProgressData
                    }
                resultMap[id] = selectedProgressData
            }

            val resultList = resultMap.values.toList()
            val readingRawState = Gson().toJson(resultList)
            articleRepository.createReadingArticleFromReadingArticleToDb(resultList)
            userRepository.updateReadingRawState(readingRawState)
        }
    }

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<ReadingArticle>>() {}.type
        return gson.fromJson(this, type)
    }
}