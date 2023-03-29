package com.arnoract.projectx.ui.lesson.model

sealed class UiLessonSentenceDetailState {
    object Loading : UiLessonSentenceDetailState()
    data class Success(val data: UiLessonSentence) : UiLessonSentenceDetailState()
}
