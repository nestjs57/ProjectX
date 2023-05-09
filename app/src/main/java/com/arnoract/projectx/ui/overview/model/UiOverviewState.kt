package com.arnoract.projectx.ui.overview.model

import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

sealed class UiOverviewState {
    object Loading : UiOverviewState()
    object NonLogin : UiOverviewState()
    data class Success(
        val hrRead: Int,
        val minusRead: Int,
        val totalRead: Int,
        val readDone: Int,
        val reading: Int,
        val lastReadArticles: List<UiArticleVerticalItem>,
        val readDoneArticles: List<UiArticleVerticalItem>
    ) : UiOverviewState()

    object NoSubscription : UiOverviewState()
}
