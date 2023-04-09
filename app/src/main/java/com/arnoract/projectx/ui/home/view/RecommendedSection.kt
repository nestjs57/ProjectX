package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel

@Composable
fun RecommendedSection(
    navHostController: NavHostController,
    models: List<UiArticleVerticalItem>,
    viewModel: HomeViewModel
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        models.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                ArticleVerticalItem(uiArticleVerticalItem) {
                    if (uiArticleVerticalItem.isPremium) {
                        viewModel.onNavigateToReading(uiArticleVerticalItem.id)
                    } else {
                        navHostController.navigate(
                            Route.readers.replace(
                                "{id}", uiArticleVerticalItem.id
                            )
                        )
                    }
                }
            }
        }
    }
}