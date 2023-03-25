package com.arnoract.projectx.ui.lesson.model

sealed class UiLessonSentenceState {
    object Loading : UiLessonSentenceState()
    data class Success(
        val comingSoon: List<UiLessonSentence>,
        val recentlyPublished: List<UiLessonSentence>
    ) : UiLessonSentenceState()
}
