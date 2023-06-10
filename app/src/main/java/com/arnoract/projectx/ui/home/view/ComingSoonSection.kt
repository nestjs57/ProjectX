package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun ComingSoonSection(
    navHostController: NavHostController,
    models: List<UiArticleVerticalItem>,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        models.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                ArticleVerticalItem(uiArticleVerticalItem, onClickSetting = {

                }) {
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