package com.arnoract.projectx.ui.lesson.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentenceByIdUseCase
import com.arnoract.projectx.ui.lesson.model.LessonSentenceToUiLessonSentenceMapper
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceDetailState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LessonSentenceDetailViewModel(
    private val id: String,
    private val getLessonSentenceByIdUseCase: GetLessonSentenceByIdUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiLessonSentenceDetailState = MutableLiveData<UiLessonSentenceDetailState>()
    val uiLessonSentenceDetailState: LiveData<UiLessonSentenceDetailState>
        get() = _uiLessonSentenceDetailState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private var textToSpeech: TextToSpeech? = null

    init {
        viewModelScope.launch {
            try {
                delay(200)
                _uiLessonSentenceDetailState.value = UiLessonSentenceDetailState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getLessonSentenceByIdUseCase.invoke(id).successOrThrow()
                }
                delay(1000)
                _uiLessonSentenceDetailState.value = UiLessonSentenceDetailState.Success(
                    data = LessonSentenceToUiLessonSentenceMapper.map(result)
                )
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun onTextSpeech(context: Context, word: String) {
        if (word.isBlank()) return
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.ENGLISH
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        word, TextToSpeech.QUEUE_ADD, null, null
                    )
                }
            }
        }
    }
}