package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun RecommendedSection(models: List<UiArticleVerticalItem>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        models.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                BookVerticalItem(uiArticleVerticalItem) {

                }
            }
        }
    }
}