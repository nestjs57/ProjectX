package com.arnoract.projectx.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOr
import com.arnoract.projectx.domain.usecase.article.GetArticleByCategoryIdUseCase
import com.arnoract.projectx.ui.category.model.UiCategoryDetailState
import com.arnoract.projectx.ui.home.model.mapper.ArticleToUiArticleVerticalItemMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryDetailViewModel(
    private val categoryId: String,
    private val getArticleByCategoryIdUseCase: GetArticleByCategoryIdUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiCategoryDetailState = MutableLiveData<UiCategoryDetailState?>()
    val uiCategoryDetailState: LiveData<UiCategoryDetailState?>
        get() = _uiCategoryDetailState

    init {
        viewModelScope.launch {
            _uiCategoryDetailState.value = UiCategoryDetailState.Loading
            delay(1000)
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getArticleByCategoryIdUseCase.invoke(categoryId).successOr(listOf())
                }
                if (result.isEmpty()) {
                    _uiCategoryDetailState.value = UiCategoryDetailState.Empty
                } else {
                    _uiCategoryDetailState.value = UiCategoryDetailState.Success(
                        data = result.map {
                            ArticleToUiArticleVerticalItemMapper.map(it)
                        },
                    )
                }
            } catch (e: Exception) {

            }
        }
    }
}