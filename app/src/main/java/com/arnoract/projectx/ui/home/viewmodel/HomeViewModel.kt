package com.arnoract.projectx.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.usecase.article.GetArticlesUseCase
import com.arnoract.projectx.ui.home.model.UiHomeState
import com.arnoract.projectx.ui.home.model.mapper.ArticleToUiArticleVerticalItemMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiHomeState = MutableLiveData<UiHomeState>()
    val uiHomeState: LiveData<UiHomeState>
        get() = _uiHomeState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        viewModelScope.launch {
            try {
                _uiHomeState.value = UiHomeState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticlesUseCase.invoke(Unit).successOrThrow()
                }
                _uiHomeState.value = UiHomeState.Success(
                    comingSoonItem = result.filter { it.isComingSoon }.map {
                        ArticleToUiArticleVerticalItemMapper.map(it).copy(isBlock = true)
                    },
                    recommendedItem = result.filter { it.isRecommend }.map {
                        ArticleToUiArticleVerticalItemMapper.map(it)
                    },
                    recentlyPublished = result.sortedBy { it.publicDate }.map {
                        ArticleToUiArticleVerticalItemMapper.map(it)
                    }
                )
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}