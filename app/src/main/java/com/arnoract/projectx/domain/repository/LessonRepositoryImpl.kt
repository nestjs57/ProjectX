package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.core.api.model.lesson.NetworkLessonSentence
import com.arnoract.projectx.core.api.model.lesson.mapper.NetworkLessonSentenceToLessonSentenceMapper
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class LessonRepositoryImpl(
    private val db: FirebaseFirestore,
) : LessonRepository {
    override suspend fun getLessonSentences(): List<LessonSentence> {
        val result = db.collection("lesson_sentence").get().await()
        return result.documents.map {
            NetworkLessonSentenceToLessonSentenceMapper.map(
                it.toObject<NetworkLessonSentence>()?.copy(id = it.id)
            )
        }
    }
}