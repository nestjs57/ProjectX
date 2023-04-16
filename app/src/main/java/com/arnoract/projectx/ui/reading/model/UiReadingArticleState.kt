package com.arnoract.projectx.ui.reading.model

import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

sealed class UiReadingArticleState {
    object Loading : UiReadingArticleState()
    data class Success(
        val data: List<UiArticleVerticalItem>
    ) : UiReadingArticleState()

    object NonLogin : UiReadingArticleState()
    object Empty : UiReadingArticleState()
    object NoSubscription : UiReadingArticleState()
}