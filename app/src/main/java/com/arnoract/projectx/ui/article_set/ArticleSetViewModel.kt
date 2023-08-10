package com.arnoract.projectx.ui.article_set

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.SubscriptionViewModelDelegate
import com.arnoract.projectx.SubscriptionViewModelDelegateImpl
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.model.article.Article
import com.arnoract.projectx.domain.model.article.ArticleSet
import com.arnoract.projectx.domain.usecase.article.GetArticleSetUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticlesUseCase
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.ui.article_set.model.UiArticleSetState
import com.arnoract.projectx.ui.article_set.model.mapper.ArticleSetToUiArticleSetMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleSetViewModel(
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val getArticleSetUseCase: GetArticleSetUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _resultArticleSet = MutableLiveData<List<ArticleSet>>()
    private val _resultArticles = MutableLiveData<List<Article>>()

    private val _uiArticleSetState = MutableLiveData<UiArticleSetState>()
    val uiArticleSetState: LiveData<UiArticleSetState>
        get() = _uiArticleSetState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        viewModelScope.launch {
            try {
                _uiArticleSetState.value = UiArticleSetState.Loading
                observeReadingArticleUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                    .collectLatest {
                        if (_resultArticleSet.value == null) {
                            val result = withContext(coroutinesDispatcherProvider.io) {
                                getArticleSetUseCase.invoke(Unit).successOrThrow()
                            }
                            _resultArticleSet.value = result
                        }
                        if (_resultArticles.value == null) {
                            val articlesResult = withContext(coroutinesDispatcherProvider.io) {
                                getArticlesUseCase.invoke(Unit).successOrThrow()
                            }

                            _resultArticles.value = articlesResult
                        }
                        val isLogin = withContext(coroutinesDispatcherProvider.io) {
                            getIsLoginUseCase.invoke(Unit)
                        }.successOr(false)
                        val data = it.successOr(listOf())
                        _uiArticleSetState.value =
                            UiArticleSetState.Success(_resultArticleSet.value?.map { articlesSet ->
                                val mArticleMatch = data.filter { local ->
                                    articlesSet.articles.any { it.trim() == local.id.trim() }
                                }
                                ArticleSetToUiArticleSetMapper(mArticleMatch).map(articlesSet)
                            } ?: listOf())
                    }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}