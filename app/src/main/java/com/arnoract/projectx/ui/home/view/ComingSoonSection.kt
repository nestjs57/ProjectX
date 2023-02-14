package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun ComingSoonSection(
    navHostController: NavHostController,
    models: List<UiArticleVerticalItem>,
) {
    LazyRow() {
        models.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                BookVerticalItem(uiArticleVerticalItem) {
                    navHostController.navigate(
                        Route.readers.replace(
                            "{id}",
                            uiArticleVerticalItem.id
                        )
                    )
                }
            }
        }
    }
}