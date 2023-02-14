package com.arnoract.projectx.ui.home.model

sealed class UiHomeState {
    object Loading : UiHomeState()
    data class Success(
        val comingSoonItem: List<UiArticleVerticalItem>,
        val recommendedItem: List<UiArticleVerticalItem>,
        val recentlyPublished: List<UiArticleVerticalItem>
    ) : UiHomeState()
}
