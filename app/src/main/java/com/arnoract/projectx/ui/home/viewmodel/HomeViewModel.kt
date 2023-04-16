package com.arnoract.projectx.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.SubscriptionViewModelDelegate
import com.arnoract.projectx.SubscriptionViewModelDelegateImpl
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
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _uiHomeState = MutableLiveData<UiHomeState>()
    val uiHomeState: LiveData<UiHomeState>
        get() = _uiHomeState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private val _navigateToReader = MutableSharedFlow<String>()
    val navigateToReader: MutableSharedFlow<String> get() = _navigateToReader

    private val _showDialogErrorNoPremium = MutableSharedFlow<Unit>()
    val showDialogErrorNoPremium: MutableSharedFlow<Unit> get() = _showDialogErrorNoPremium

    init {
        viewModelScope.launch {
            try {
                _uiHomeState.value = UiHomeState.Loading
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticlesUseCase.invoke(Unit).successOrThrow()
                }
                _uiHomeState.value = UiHomeState.Success(
                    comingSoonItem = result.sortedBy { it.publicDate }
                        .filter { it.isComingSoon }
                        .map {
                            ArticleToUiArticleVerticalItemMapper.map(it)
                                .copy(isBlock = true, isEnablePremium = true)
                        },
                    recommendedItem = result.sortedByDescending { it.publicDate }
                        .filter { it.isRecommend && !it.isComingSoon }.map {
                            ArticleToUiArticleVerticalItemMapper.map(it)
                                .copy(isEnablePremium = true)
                        }.take(5),
                    recentlyPublished = result.sortedByDescending { it.publicDate }
                        .filter { !it.isComingSoon }.map {
                            ArticleToUiArticleVerticalItemMapper.map(it)
                                .copy(isEnablePremium = true)
                        }.take(5)
                )
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun onNavigateToReading(id: String) {
        viewModelScope.launch {
            val isSubscription = getIsSubscription()
            if (isSubscription) {
                _navigateToReader.emit(id)
            } else {
                _showDialogErrorNoPremium.emit(Unit)
            }
        }
    }
}