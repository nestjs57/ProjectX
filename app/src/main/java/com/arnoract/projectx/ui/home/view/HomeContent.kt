package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem

@Composable
fun HomeContent(
    navHostController: NavHostController,
    comingSoon: List<UiArticleVerticalItem>,
    recommendedItem: List<UiArticleVerticalItem>,
    recentlyPublished: List<UiArticleVerticalItem>,
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        if (comingSoon.isNotEmpty()) {
            ContentTitleSection(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
                icon = painterResource(id = R.drawable.ic_time_fast),
                value = stringResource(id = R.string.coming_soon_label),
                onClickedSeeMore = {

                }
            )
            ComingSoonSection(navHostController, comingSoon)
        }
        if (recommendedItem.isNotEmpty()) {
            ContentTitleSection(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
                icon = painterResource(id = R.drawable.ic_like),
                value = stringResource(id = R.string.recommended_label),
                onClickedSeeMore = {

                }
            )
            RecommendedSection(navHostController, recommendedItem)
        }
        if (recentlyPublished.isNotEmpty()) {
            ContentTitleSection(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
                icon = painterResource(id = R.drawable.ic_rocket),
                value = stringResource(id = R.string.public_last_label),
                isShowSeeAll = true,
                onClickedSeeMore = {
                    navHostController.navigate(
                        Route.category_detail.replace(
                            "{categoryId}",
                            "0"
                        )
                    )
                }
            )
            RecentlyPublishedSection(navHostController, recentlyPublished)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}