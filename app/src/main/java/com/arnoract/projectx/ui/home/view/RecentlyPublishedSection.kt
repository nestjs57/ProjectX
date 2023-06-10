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
fun RecentlyPublishedSection(
    navHostController: NavHostController,
    models: List<UiArticleVerticalItem>,
    viewModel: HomeViewModel
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        models.forEachIndexed { _, uiArticleHorizontalItem ->
            item {
                ArticleVerticalItem(uiArticleHorizontalItem, onClickSetting = {

                }) {
                    if (uiArticleHorizontalItem.isPremium) {
                        viewModel.onNavigateToReading(uiArticleHorizontalItem.id)
                    } else {
                        navHostController.navigate(
                            Route.readers.replace(
                                "{id}", uiArticleHorizontalItem.id
                            )
                        )
                    }
                }
            }
        }
    }
}