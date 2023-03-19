package com.arnoract.projectx.ui.reader.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.usecase.article.*
import com.arnoract.projectx.ui.reader.model.*
import com.arnoract.projectx.ui.reader.model.mapper.ParagraphToUiParagraphMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ReaderViewModel(
    private val id: String,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val getCurrentParagraphFromDbUseCase: GetCurrentParagraphFromDbUseCase,
    private val setCurrentParagraphToDbUseCase: SetCurrentParagraphToDbUseCase,
    private val getFontSizeSettingUseCase: GetFontSizeSettingUseCase,
    private val setFontSizeSettingUseCase: SetFontSizeSettingUseCase,
    private val getBackgroundModelSettingUseCase: GetBackgroundModelSettingUseCase,
    private val setBackgroundModelSettingUseCase: SetBackgroundModelSettingUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiReaderState = MutableLiveData<UiReaderState>()
    val uiReaderState: LiveData<UiReaderState>
        get() = _uiReaderState

    private val _readerSetting = MutableLiveData<ReaderSetting>()
    val readerSetting: LiveData<ReaderSetting>
        get() = _readerSetting

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private var textToSpeech: TextToSpeech? = null

    init {
        viewModelScope.launch {

            try {
                val fontSizeSettingPref = withContext(coroutinesDispatcherProvider.io) {
                    getFontSizeSettingUseCase.invoke(Unit).successOr(SettingFontSize.NORMAL)
                }
                val backgroundModeSettingPref = withContext(coroutinesDispatcherProvider.io) {
                    getBackgroundModelSettingUseCase.invoke(Unit).successOr(SettingBackground.DAY)
                }
                _readerSetting.value = ReaderSetting(
                    fontSizeMode = fontSizeSettingPref,
                    backgroundMode = backgroundModeSettingPref
                )
                _uiReaderState.value = UiReaderState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticleByIdUseCase.invoke(id).successOrThrow()
                }
                val currentParagraphDb = withContext(coroutinesDispatcherProvider.io) {
                    getCurrentParagraphFromDbUseCase.invoke(id).successOr(0)
                }
                delay(1000)
                _uiReaderState.value = UiReaderState.Success(titleTh = result.titleTh,
                    titleEn = result.titleEn,
                    currentParagraphSelected = currentParagraphDb,
                    uiParagraph = result.paragraphsVocabulary?.map { param ->
                        param.map { ParagraphToUiParagraphMapper.map(it) }
                    } ?: listOf(),
                    uiTranSlateParagraph = result.paragraphTranslate)
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
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
                currentParagraphSelected = data.currentParagraphSelected.plus(1),
                uiTranSlateParagraph = data.uiTranSlateParagraph
            )
            setCurrentProgress(data.currentParagraphSelected.plus(1))
        }
    }

    fun onClickedPreviousParagraph() {
        if (_uiReaderState.value is UiReaderState.Success) {
            val data = (_uiReaderState.value as UiReaderState.Success)
            _uiReaderState.value = UiReaderState.Success(
                titleTh = data.titleTh,
                titleEn = data.titleEn,
                uiParagraph = data.uiParagraph,
                currentParagraphSelected = data.currentParagraphSelected.minus(1),
                uiTranSlateParagraph = data.uiTranSlateParagraph
            )
            setCurrentProgress(data.currentParagraphSelected.minus(1))
        }
    }

    private fun setCurrentProgress(progress: Int) {
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    setCurrentParagraphToDbUseCase.invoke(
                        SetCurrentParagraphToDbUseCase.Params(
                            id, progress
                        )
                    ).successOrThrow()
                }
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

    fun setFontSizeSetting(value: SettingFontSize) {
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    setFontSizeSettingUseCase.invoke(value).successOrThrow()
                }
                _readerSetting.value = _readerSetting.value?.copy(fontSizeMode = value)
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun setBackgroundModeSetting(value: SettingBackground) {
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    setBackgroundModelSettingUseCase.invoke(value).successOrThrow()
                }
                _readerSetting.value = _readerSetting.value?.copy(backgroundMode = value)
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}