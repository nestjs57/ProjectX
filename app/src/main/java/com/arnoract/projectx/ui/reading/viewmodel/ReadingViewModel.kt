package com.arnoract.projectx.ui.reading.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.SubscriptionViewModelDelegate
import com.arnoract.projectx.SubscriptionViewModelDelegateImpl
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.domain.usecase.article.RemoveReadingArticleByIdUseCase
import com.arnoract.projectx.domain.usecase.article.SyncDataReadingUseCase
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.reading.mapper.ReadingArticleToUiArticleVerticalItemMapper
import com.arnoract.projectx.ui.reading.model.UiReadingArticleState
import com.arnoract.projectx.ui.reading.model.UiReadingFilter
import com.arnoract.projectx.util.setValueIfNew
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadingViewModel(
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val syncDataReadingUseCase: SyncDataReadingUseCase,
    private val removeReadingArticleByIdUseCase: RemoveReadingArticleByIdUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private var readingArticles = listOf<ReadingArticle>()

    private val _uiReadingState = MutableLiveData<UiReadingArticleState>()
    val uiReadingState: LiveData<UiReadingArticleState>
        get() = _uiReadingState

    private val _uiReadingFilter = MutableLiveData(UiReadingFilter.TOTAL)
    val uiReadingFilter: LiveData<UiReadingFilter>
        get() = _uiReadingFilter

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private val _openDialogDemoFeature = MutableSharedFlow<Unit>()
    val openDialogDemoFeature: MutableSharedFlow<Unit> get() = _openDialogDemoFeature

    private val _openDialogSetting = MutableSharedFlow<UiArticleVerticalItem>()
    val openDialogSetting: MutableSharedFlow<UiArticleVerticalItem> get() = _openDialogSetting

    private val _openDialogConfirmRemove = MutableSharedFlow<Unit>()
    val openDialogConfirmRemove: MutableSharedFlow<Unit> get() = _openDialogConfirmRemove

    private val _sycing = MutableLiveData<Boolean>()
    val sycing: LiveData<Boolean> get() = _sycing

    init {
        viewModelScope.launch {
            try {
                _uiReadingState.value = UiReadingArticleState.Loading
                observeReadingArticleUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                    .collectLatest {
                        val isLogin = withContext(coroutinesDispatcherProvider.io) {
                            getIsLoginUseCase.invoke(Unit)
                        }.successOr(false)
                        if (isLogin) {
                            val data = it.successOr(listOf())
                            readingArticles = data
                            setFilter(_uiReadingFilter.value)
                        } else {
                            _uiReadingState.value = UiReadingArticleState.NonLogin
                        }
                    }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun setFilter(filter: UiReadingFilter?) {
        _uiReadingFilter.setValueIfNew(filter)
        val newData: List<ReadingArticle> = when (filter) {
            UiReadingFilter.TOTAL -> {
                readingArticles.sortedByDescending { it.lastDate }
            }
            UiReadingFilter.COMPLETE -> {
                readingArticles.sortedByDescending { it.lastDate }
                    .filter { it.currentParagraph.plus(1) == it.totalParagraph }
            }
            UiReadingFilter.READING -> {
                readingArticles.sortedByDescending { it.lastDate }
                    .filter { it.currentParagraph.plus(1) != it.totalParagraph }
            }
            else -> {
                readingArticles.sortedByDescending { it.lastDate }
            }
        }

        viewModelScope.launch {
            if (!getIsSubscription()) {
                _uiReadingState.value = UiReadingArticleState.NoSubscription
            } else {
                if (newData.isEmpty()) {
                    _uiReadingState.value = UiReadingArticleState.Empty
                } else {
                    _uiReadingState.setValueIfNew(UiReadingArticleState.Success(data = newData.map { readingArticle ->
                        ReadingArticleToUiArticleVerticalItemMapper.map(
                            readingArticle
                        ).copy(isShowColonSetting = true)
                    }))
                }
            }
        }
    }

    fun onSyncDataReadingUseCase() {
        viewModelScope.launch {
            val isLogin = withContext(coroutinesDispatcherProvider.io) {
                getIsLoginUseCase.invoke(Unit)
            }.successOr(false)
            if (!isLogin) return@launch
            _sycing.value = true
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    syncDataReadingUseCase.invoke(Unit).successOrThrow()
                }
            } catch (e: java.lang.Exception) {
                error.emit(e.message ?: "")
            } finally {
                _sycing.value = false
            }
        }
    }

    fun onOpenDialogDemoFeature() {
        viewModelScope.launch {
            _openDialogDemoFeature.emit(Unit)
        }
    }

    fun openDialogSetting(data: UiArticleVerticalItem) {
        viewModelScope.launch {
            _openDialogSetting.emit(data)
        }
    }

    fun openDialogConfirmDelete() {
        viewModelScope.launch {
            _openDialogConfirmRemove.emit(Unit)
        }
    }

    fun deleteFromReading(id: String) {
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    removeReadingArticleByIdUseCase.invoke(id).successOrThrow()
                }
            } catch (e: java.lang.Exception) {
                error.emit(e.message ?: "")
            }
        }
    }
}
