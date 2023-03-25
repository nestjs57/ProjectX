package com.arnoract.projectx.domain.repository

import com.arnoract.projectx.domain.model.lesson.LessonSentence

interface LessonRepository {
    suspend fun getLessonSentences(): List<LessonSentence>
}