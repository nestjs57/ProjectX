package com.arnoract.projectx.ui.category.model

import com.arnoract.projectx.ui.home.model.UiArticleHorizontalItem
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

sealed class UiCategoryDetailState {
    object Loading : UiCategoryDetailState()
    data class Success(
        val data: List<UiArticleHorizontalItem>,
    ) : UiCategoryDetailState()
    object Empty : UiCategoryDetailState()
}
