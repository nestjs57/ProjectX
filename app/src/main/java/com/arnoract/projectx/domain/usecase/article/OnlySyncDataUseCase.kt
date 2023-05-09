package com.arnoract.projectx.domain.usecase.article

import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.UseCase
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.repository.ArticleRepository
import com.arnoract.projectx.domain.repository.UserRepository
import com.google.gson.reflect.TypeToken
import java.util.*

class OnlySyncDataUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : UseCase<OnlySyncDataUseCase.Params, Unit>() {
    override suspend fun execute(parameters: Params) {
        val currentUser = userRepository.getUser()
        val currentReadingFromDb = articleRepository.getReadingArticles()

        if (currentUser.readingRawState.isBlank()) {
            val readingRawState = MyGsonBuilder().build().toJson(currentReadingFromDb)
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

            val dataDiff = parameters.lastDate.time - parameters.firstDate.time
            var resultList = resultMap.values.toList()
            resultList = resultList.map {
                if (it.id == parameters.id) {
                    it.copy(
                        currentParagraph = parameters.progress,
                        firstDate = it.firstDate ?: parameters.firstDate,
                        lastDate = parameters.lastDate,
                        totalReadTime = it.totalReadTime.plus(dataDiff.toInt())
                    )
                } else {
                    it
                }
            }
            val readingRawState = MyGsonBuilder().build().toJson(resultList)
            articleRepository.createReadingArticleFromReadingArticleToDb(resultList)
            userRepository.updateReadingRawState(readingRawState)
        }
    }

    data class Params(
        val id: String,
        val progress: Int,
        val firstDate: Date,
        val lastDate: Date
    )

    private fun <T : Any?> String.toArrayClass(): ArrayList<T> {
        val gson = MyGsonBuilder().build()
        val type = object : TypeToken<ArrayList<ReadingArticle>>() {}.type
        return gson.fromJson(this, type)
    }
}