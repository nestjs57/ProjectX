package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.lesson.NetworkLessonSentence
import com.arnoract.projectx.core.api.model.lesson.mapper.NetworkLessonSentenceToLessonSentenceMapper
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.pref.UserPreferenceStorage
import com.arnoract.projectx.domain.repository.exception.NotEnoughCoinException
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class LessonRepositoryImpl(
    private val db: FirebaseFirestore, private val userPreferenceStorage: UserPreferenceStorage
) : LessonRepository {
    override suspend fun getLessonSentences(): List<LessonSentence> {
        val result = db.collection("lesson_sentence").get().await()
        return result.documents.map {
            NetworkLessonSentenceToLessonSentenceMapper.map(
                it.toObject<NetworkLessonSentence>()?.copy(id = it.id)
            )
        }
    }

    override suspend fun getLessonSentence(id: String): LessonSentence {
        val result =
            db.collection("lesson_sentence").whereEqualTo(FieldPath.documentId(), id).get().await()
        return NetworkLessonSentenceToLessonSentenceMapper.map(
            result.documents.firstOrNull()?.toObject<NetworkLessonSentence>()?.copy(id = id)
        )
    }

    override suspend fun accessContentWithCoin(coin: Int): Int {
        val currentCoin = userPreferenceStorage.coin
        val isNotEnoughCoin = (currentCoin ?: 0) < coin
        if (isNotEnoughCoin) throw NotEnoughCoinException()

        val totalCoin = (currentCoin ?: 0).minus(coin)
        db.collection("users").document(userPreferenceStorage.userId ?: "")
            .update("coin", totalCoin)
        userPreferenceStorage.coin = totalCoin
        return totalCoin
    }
}