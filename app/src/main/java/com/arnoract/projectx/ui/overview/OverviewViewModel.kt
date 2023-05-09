package com.arnoract.projectx.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.SubscriptionViewModelDelegate
import com.arnoract.projectx.SubscriptionViewModelDelegateImpl
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.domain.usecase.overview.GetOverviewUseCase
import com.arnoract.projectx.ui.overview.model.UiOverviewState
import com.arnoract.projectx.ui.overview.model.mapper.ReadingArticleToVerticalItemMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class OverviewViewModel(
    private val getOverviewUseCase: GetOverviewUseCase,
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _uiOverviewState = MutableLiveData<UiOverviewState>()
    val uiOverviewState: LiveData<UiOverviewState> get() = _uiOverviewState

    init {
        viewModelScope.launch {
            _uiOverviewState.value = UiOverviewState.Loading
            observeReadingArticleUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                .collectLatest {
                    val isLogin = withContext(coroutinesDispatcherProvider.io) {
                        getIsLoginUseCase.invoke(Unit)
                    }.successOr(false)
                    if (isLogin) {
                        if (!getIsSubscription()) {
                            _uiOverviewState.value = UiOverviewState.NoSubscription
                            return@collectLatest
                        }
                        val data = it.successOr(listOf())
                        var timeMilli = 0L
                        data.forEachIndexed { _, readingArticle ->
                            timeMilli = timeMilli.plus(readingArticle.totalReadTime)
                        }

                        _uiOverviewState.value = UiOverviewState.Success(
                            hrRead = TimeUnit.MILLISECONDS.toHours(timeMilli).toInt(),
                            minusRead = TimeUnit.MILLISECONDS.toMinutes(timeMilli).mod(60),
                            totalRead = data.size,
                            readDone = data.filter { readingArticle ->
                                readingArticle.currentParagraph.plus(1) == readingArticle.totalParagraph
                            }.size,
                            reading = data.filter { readingArticle ->
                                readingArticle.currentParagraph.plus(1) != readingArticle.totalParagraph
                            }.size,
                            lastReadArticles = data.filter { it.currentParagraph.plus(1) != it.totalParagraph }
                                .sortedByDescending { it.lastDate }.map { readingArticle ->
                                    ReadingArticleToVerticalItemMapper.map(readingArticle)
                                        .copy(isEnablePremium = true)
                                },
                            readDoneArticles = data.filter { it.currentParagraph.plus(1) == it.totalParagraph }
                                .sortedByDescending { it.lastDate }.map { readingArticle ->
                                    ReadingArticleToVerticalItemMapper.map(readingArticle)
                                        .copy(isEnablePremium = true)
                                }
                        )
                    } else {
                        _uiOverviewState.value = UiOverviewState.NonLogin
                    }
                }
        }
    }
}