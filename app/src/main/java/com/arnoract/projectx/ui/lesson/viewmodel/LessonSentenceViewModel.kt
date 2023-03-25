package com.arnoract.projectx.ui.lesson.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentencesUseCase
import com.arnoract.projectx.ui.lesson.model.LessonSentenceToUiLessonSentenceMapper
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonSentenceViewModel(
    private val getLessonSentencesUseCase: GetLessonSentencesUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiLessonState = MutableLiveData<UiLessonSentenceState>()
    val uiLessonState: LiveData<UiLessonSentenceState>
        get() = _uiLessonState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        getLessonSentences()
    }

    private fun getLessonSentences() {
        viewModelScope.launch {
            try {
                _uiLessonState.value = UiLessonSentenceState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getLessonSentencesUseCase.invoke(Unit).successOrThrow()
                }
                val comingSoon = result.filter { it.isComingSoon }.map {
                    LessonSentenceToUiLessonSentenceMapper.map(it)
                }
                val recentlyPublished = result.filter { !it.isComingSoon }.map {
                    LessonSentenceToUiLessonSentenceMapper.map(it)
                }
                _uiLessonState.value = UiLessonSentenceState.Success(
                    comingSoon = comingSoon, recentlyPublished = recentlyPublished
                )
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}