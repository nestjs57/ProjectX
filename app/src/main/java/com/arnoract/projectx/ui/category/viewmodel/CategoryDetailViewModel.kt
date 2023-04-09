package com.arnoract.projectx.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.SubscriptionViewModelDelegate
import com.arnoract.projectx.SubscriptionViewModelDelegateImpl
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.usecase.article.GetArticleByCategoryIdUseCase
import com.arnoract.projectx.ui.category.model.UiCategoryDetailState
import com.arnoract.projectx.ui.home.model.UiArticleHorizontalItem
import com.arnoract.projectx.ui.home.model.mapper.ArticleToUiArticleHorizontalItemMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryDetailViewModel(
    private val categoryId: String,
    private val getArticleByCategoryIdUseCase: GetArticleByCategoryIdUseCase,
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _uiCategoryDetailState = MutableLiveData<UiCategoryDetailState?>()
    val uiCategoryDetailState: LiveData<UiCategoryDetailState?>
        get() = _uiCategoryDetailState

    private val _navigateToReader = MutableSharedFlow<String>()
    val navigateToReader: MutableSharedFlow<String> get() = _navigateToReader

    private val _showDialogErrorNoPremium = MutableSharedFlow<Unit>()
    val showDialogErrorNoPremium: MutableSharedFlow<Unit> get() = _showDialogErrorNoPremium

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        viewModelScope.launch {
            _uiCategoryDetailState.value = UiCategoryDetailState.Loading
            delay(500)
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticleByCategoryIdUseCase.invoke(categoryId).successOr(listOf())
                }
                val data = result.sortedByDescending { it.publicDate }
                    .filter { !it.isComingSoon }.map {
                        ArticleToUiArticleHorizontalItemMapper.map(it)
                    }
                if (data.isEmpty()) {
                    _uiCategoryDetailState.value = UiCategoryDetailState.Empty
                } else {
                    _uiCategoryDetailState.value = UiCategoryDetailState.Success(
                        data = data,
                    )
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun onNavigateToReader(article: UiArticleHorizontalItem) {
        viewModelScope.launch {
            if (article.isPremium) {
                if (getIsSubscription()) {
                    onNavigateToReader(article.id)
                } else {
                    _showDialogErrorNoPremium.emit(Unit)
                }
            } else {
                onNavigateToReader(article.id)
            }
        }
    }

    private fun onNavigateToReader(id: String) {
        viewModelScope.launch {
            _navigateToReader.emit(id)
            if (_uiCategoryDetailState.value is UiCategoryDetailState.Success) {
                val model = _uiCategoryDetailState.value as UiCategoryDetailState.Success
                _uiCategoryDetailState.value = model.copy(data = model.data.map {
                    if (it.id == id) {
                        it.copy(viewCount = it.viewCount.plus(1))
                    } else {
                        it
                    }
                })
            }
        }
    }
}