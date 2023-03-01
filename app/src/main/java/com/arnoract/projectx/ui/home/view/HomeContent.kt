package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.model.UiArticleCategory
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun HomeContent(
    navHostController: NavHostController,
    recommended: List<UiArticleVerticalItem>,
    recommendedItem: List<UiArticleVerticalItem>,
    recentlyPublished: List<UiArticleVerticalItem>,
) {
    val scrollState = rememberScrollState()
    Column( modifier = Modifier.verticalScroll(scrollState)) {
        ContentTitleSection(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
            value = stringResource(id = R.string.coming_soon_label)
        )
        ComingSoonSection(navHostController, recommended)
        ContentTitleSection(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
            value = stringResource(id = R.string.recommended_label)
        )
        RecommendedSection(recommendedItem)
        ContentTitleSection(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
            value = stringResource(id = R.string.public_last_label)
        )
        RecentlyPublishedSection(navHostController,recentlyPublished)
    }
}