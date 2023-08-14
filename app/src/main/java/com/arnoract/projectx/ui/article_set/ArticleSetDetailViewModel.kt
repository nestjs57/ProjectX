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
import com.arnoract.projectx.domain.usecase.article.GetArticleSetByIdUseCase
import com.arnoract.projectx.domain.usecase.article.GetArticlesUseCase
import com.arnoract.projectx.domain.usecase.article.GetIsLoginUseCase
import com.arnoract.projectx.domain.usecase.article.ObserveReadingArticleUseCase
import com.arnoract.projectx.ui.article_set.model.UiArticleSetDetailState
import com.arnoract.projectx.ui.article_set.model.mapper.ArticleToUiArticleSetDetailMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleSetDetailViewModel(
    private val articleSetId: String,
    private val observeReadingArticleUseCase: ObserveReadingArticleUseCase,
    private val getArticleSetByIdUseCase: GetArticleSetByIdUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val subscriptionViewModelDelegateImpl: SubscriptionViewModelDelegateImpl,
    private val getIsLoginUseCase: GetIsLoginUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
) : ViewModel(), SubscriptionViewModelDelegate by subscriptionViewModelDelegateImpl {

    private val _articleSetById = MutableLiveData<ArticleSet>()
    private val _resultArticles = MutableLiveData<List<Article>>()

    private val _uiArticleSetDetailState = MutableLiveData<UiArticleSetDetailState?>()
    val uiArticleSetDetailState: LiveData<UiArticleSetDetailState?>
        get() = _uiArticleSetDetailState

    private val _toolbarColor = MutableLiveData<String>("#ffffff")
    val toolbarColor: LiveData<String>
        get() = _toolbarColor

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        viewModelScope.launch {
            try {
                _uiArticleSetDetailState.value = UiArticleSetDetailState.Loading
                observeReadingArticleUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                    .collectLatest {
                        if (_articleSetById.value == null) {
                            val articleSet = withContext(coroutinesDispatcherProvider.io) {
                                getArticleSetByIdUseCase.invoke(articleSetId).successOrThrow()
                            }
                            _articleSetById.value = articleSet
                        }
                        if (_resultArticles.value == null) {
                            val articlesResult = withContext(coroutinesDispatcherProvider.io) {
                                getArticlesUseCase.invoke(Unit).successOrThrow()
                            }
                            _resultArticles.value =
                                articlesResult.filter { article -> article.paragraphTranslate.isNotEmpty() }
                        }

                        val data = _articleSetById.value?.articles?.map { articleId ->
                            _resultArticles.value?.find { article ->
                                articleId == article.id
                            }
                        }
                        val dataFromLocal = it.successOr(listOf())
                        val resultMapper = data?.map { article ->
                            val currentReadParagraph = dataFromLocal.find { local ->
                                local.id == article?.id
                            }?.currentParagraph
                            ArticleToUiArticleSetDetailMapper(
                                currentReadParagraph?.plus(1) ?: 0
                            ).map(article)
                        } ?: listOf()
                        _uiArticleSetDetailState.value =
                            UiArticleSetDetailState.Success(resultMapper)
                        _toolbarColor.value = _articleSetById.value?.backgroundColor
                    }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}