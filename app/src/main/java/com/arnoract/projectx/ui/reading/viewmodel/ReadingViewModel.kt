package com.arnoract.projectx.ui.reading.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.ui.reading.mapper.ReadingArticleToUiArticleVerticalItemMapper
import com.arnoract.projectx.ui.reading.model.UiReadingArticleState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadingViewModel(
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiReadingState = MutableLiveData<UiReadingArticleState>()
    val uiReadingState: LiveData<UiReadingArticleState>
        get() = _uiReadingState

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
                            if (data.isEmpty()) {
                                _uiReadingState.value = UiReadingArticleState.Empty
                            } else {
                                _uiReadingState.value =
                                    UiReadingArticleState.Success(data = data.map { readingArticle ->
                                        ReadingArticleToUiArticleVerticalItemMapper.map(
                                            readingArticle
                                        )
                                    })
                            }
                        } else {
                            _uiReadingState.value = UiReadingArticleState.NonLogin
                        }
                    }
            } catch (e: Exception) {

            }
        }
    }
}
