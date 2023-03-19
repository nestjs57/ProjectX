package com.arnoract.projectx.ui.reading.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.model.article.ReadingArticle
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
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
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

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

    init {
        println()
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
                readingArticles
            }
            UiReadingFilter.COMPLETE -> {
                readingArticles.filter { it.currentParagraph.plus(1) == it.totalParagraph }
            }
            UiReadingFilter.READING -> {
                readingArticles.filter { it.currentParagraph.plus(1) != it.totalParagraph }
            }
            else -> {
                readingArticles
            }
        }

        if (newData.isEmpty()) {
            _uiReadingState.value = UiReadingArticleState.Empty
        } else {
            _uiReadingState.setValueIfNew(UiReadingArticleState.Success(data = newData.map { readingArticle ->
                ReadingArticleToUiArticleVerticalItemMapper.map(
                    readingArticle
                )
            }))
        }
    }
}
