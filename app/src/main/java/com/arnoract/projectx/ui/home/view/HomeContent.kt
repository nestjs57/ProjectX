package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun HomeContent(
    navHostController: NavHostController,
    recommended: List<UiArticleVerticalItem>,
    recommendedItem: List<UiArticleVerticalItem>,
    recentlyPublished: List<UiArticleVerticalItem>,
) {
    LazyColumn() {
        item {
            ContentTitleSection(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
                value = stringResource(id = R.string.coming_soon_label)
            )
        }
        item {
            ComingSoonSection(navHostController, recommended)
        }
        item {
            ContentTitleSection(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
                value = stringResource(id = R.string.recommended_label)
            )
        }
        item {
            RecommendedSection(recommendedItem)
        }
    }
}