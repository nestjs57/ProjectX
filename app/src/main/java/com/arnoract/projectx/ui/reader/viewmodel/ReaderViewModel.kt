package com.arnoract.projectx.ui.reader.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.usecase.article.GetArticleByIdUseCase
import com.arnoract.projectx.ui.reader.model.UiParagraph
import com.arnoract.projectx.ui.reader.model.UiReaderState
import com.arnoract.projectx.ui.reader.model.mapper.ParagraphToUiParagraphMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ReaderViewModel(
    private val id: String,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {


    private val _uiReaderState = MutableLiveData<UiReaderState>()
    val uiReaderState: LiveData<UiReaderState>
        get() = _uiReaderState

    private var textToSpeech: TextToSpeech? = null

    init {
        viewModelScope.launch {

            try {
                _uiReaderState.value = UiReaderState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticleByIdUseCase.invoke(id).successOrThrow()
                }
                delay(1000)
                _uiReaderState.value = UiReaderState.Success(
                    titleTh = result.titleTh,
                    titleEn = result.titleEn,
                    uiParagraph = result.paragraphs?.map { param ->
                        param.map { ParagraphToUiParagraphMapper.map(it) }
                    } ?: listOf()
                )
            } catch (e: Exception) {

            }
        }
    }

    fun setSelectedParagraph(value: UiParagraph) {
        if (_uiReaderState.value is UiReaderState.Success) {
            val data =
                (_uiReaderState.value as UiReaderState.Success).uiParagraph.mapIndexed { index, paragraph ->
                    if (value.paragraphNum.minus(1) == index) {
                        paragraph.map {
                            it.copy(
                                isSelected = if (it.id == value.id) {
                                    !it.isSelected
                                } else {
                                    false
                                }
                            )
                        }
                    } else {
                        paragraph
                    }
                }
            _uiReaderState.value =
                (_uiReaderState.value as UiReaderState.Success).copy(uiParagraph = data)
        }
    }

    fun onClickedNextParagraph() {
        if (_uiReaderState.value is UiReaderState.Success) {
            val data = (_uiReaderState.value as UiReaderState.Success)
            _uiReaderState.value = UiReaderState.Success(
                titleTh = data.titleTh,
                titleEn = data.titleEn,
                uiParagraph = data.uiParagraph,
                currentParagraphSelected = data.currentParagraphSelected.plus(1)
            )
        }
    }

    fun onClickedPreviousParagraph() {
        if (_uiReaderState.value is UiReaderState.Success) {
            val data = (_uiReaderState.value as UiReaderState.Success)
            _uiReaderState.value = UiReaderState.Success(
                titleTh = data.titleTh,
                titleEn = data.titleEn,
                uiParagraph = data.uiParagraph,
                currentParagraphSelected = data.currentParagraphSelected.minus(1)
            )
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
                        word,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
        }
    }
}