package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun RecommendedSection(models: List<UiArticleVerticalItem>) {
    LazyRow() {
        models.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                BookVerticalItem(uiArticleVerticalItem) {

                }
            }
        }
    }
}