package com.arnoract.projectx.ui.article_set.model

sealed class UiArticleSetState {
    object Loading : UiArticleSetState()
    data class Success(
        val data: List<UiArticleSet>
    ) : UiArticleSetState()
}
