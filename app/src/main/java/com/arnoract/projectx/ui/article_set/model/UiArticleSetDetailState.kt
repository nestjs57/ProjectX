package com.arnoract.projectx.ui.article_set.model

sealed class UiArticleSetDetailState {
    object Loading : UiArticleSetDetailState()
    data class Success(
        val data: List<UiArticleSetDetail>
    ) : UiArticleSetDetailState()
}

