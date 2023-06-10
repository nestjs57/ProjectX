package com.arnoract.projectx.ui.lesson.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.model.lesson.LessonSentence
import com.arnoract.projectx.domain.repository.exception.NotEnoughCoinException
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.lesson.AccessContentWithCoinUseCase
import com.arnoract.projectx.domain.usecase.lesson.GetLessonSentencesUseCase
import com.arnoract.projectx.ui.lesson.model.LessonSentenceToUiLessonSentenceMapper
import com.arnoract.projectx.ui.lesson.model.UiLessonSentence
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceState
import com.arnoract.projectx.ui.lesson.ui.ConfirmPurchaseModel
import com.arnoract.projectx.ui.lesson.ui.ConfirmPurchaseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonSentenceViewModel(
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val getLessonSentencesUseCase: GetLessonSentencesUseCase,
    private val accessContentWithCoinUseCase: AccessContentWithCoinUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiLessonState = MutableLiveData<UiLessonSentenceState>()
    val uiLessonState: LiveData<UiLessonSentenceState>
        get() = _uiLessonState

    private val _showDialogRequireLogin = MutableSharedFlow<Unit>()
    val showDialogRequireLogin: MutableSharedFlow<Unit>
        get() = _showDialogRequireLogin

    private val _showDialogConfirmPurchase = MutableSharedFlow<ConfirmPurchaseState>()
    val showDialogConfirmPurchase: MutableSharedFlow<ConfirmPurchaseState>
        get() = _showDialogConfirmPurchase

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private val _accessContentWithCoinSuccessEvent = MutableSharedFlow<Pair<Int, Int>>()
    val accessContentWithCoinSuccessEvent: MutableSharedFlow<Pair<Int, Int>>
        get() = _accessContentWithCoinSuccessEvent

    private val _navigateToLessonSentenceDetailEvent = MutableSharedFlow<String>()
    val navigateToLessonSentenceDetailEvent: MutableSharedFlow<String>
        get() = _navigateToLessonSentenceDetailEvent

    private val _notEnoughCoinEvent = MutableSharedFlow<Unit>()
    val notEnoughCoinEvent: MutableSharedFlow<Unit>
        get() = _notEnoughCoinEvent

    private val _lessonSentence = MutableLiveData<List<LessonSentence>>()

    init {
        getLessonSentences()
    }

    private fun getLessonSentences() {
        viewModelScope.launch {
            try {
                delay(300)
                _uiLessonState.value = UiLessonSentenceState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getLessonSentencesUseCase.invoke(Unit).successOrThrow()
                }
                _lessonSentence.value = result
                val comingSoon =
                    result.sortedByDescending { it.publicDate }
                        .filter { it.isComingSoon && it.isPublic }
                        .map {
                            LessonSentenceToUiLessonSentenceMapper.map(it)
                        }
                val recentlyPublished =
                    result.sortedByDescending { it.publicDate }
                        .filter { !it.isComingSoon && it.isPublic }
                        .map {
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

    fun onClickedLessonItem(model: UiLessonSentence) {
        viewModelScope.launch {
            _navigateToLessonSentenceDetailEvent.emit(model.id)
            return@launch
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getIsLoginUseCase.invoke(Unit).successOrThrow()
                }
                if (!result) {
                    _showDialogRequireLogin.emit(Unit)
                } else {
                    _showDialogConfirmPurchase.emit(
                        ConfirmPurchaseState.Show(
                            ConfirmPurchaseModel(
                                id = model.id,
                                titleTh = model.titleTh,
                                coin = model.priceCoin
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun accessContentWithCoin(model: ConfirmPurchaseModel) {
        viewModelScope.launch {
            try {
                val coinToUse = _lessonSentence.value?.find { it.id == model.id }?.priceCoin
                val result = withContext(coroutinesDispatcherProvider.io) {
                    accessContentWithCoinUseCase.invoke(coinToUse ?: 0).successOrThrow()
                }
                _accessContentWithCoinSuccessEvent.emit(Pair(coinToUse ?: 0, result))
                _navigateToLessonSentenceDetailEvent.emit(model.id)
            } catch (e: NotEnoughCoinException) {
                _notEnoughCoinEvent.emit(Unit)
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}