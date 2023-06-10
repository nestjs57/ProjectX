package com.arnoract.projectx.ui.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.view.ArticleVerticalItem
import com.arnoract.projectx.ui.home.view.ContentTitleSection
import com.arnoract.projectx.ui.overview.model.UiOverviewState

@Composable
fun OverviewContent(data: UiOverviewState.Success?, navController: NavHostController) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
                Overview(data)
            }
            item {
                ReadDone(data = data, navController = navController)
            }
            item {
                LastRead(data, navController)
            }
            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}

@Composable
private fun Overview(data: UiOverviewState.Success?) {
    LazyVerticalGrid(
        modifier = Modifier.height(240.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(2) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_history),
                            contentDescription = "Navigation Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.time_of_read_label),
                            modifier = Modifier,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray700),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${data?.hrRead} ชม. ${data?.minusRead} นาที",
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_auto_stories),
                            contentDescription = "Navigation Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.total_read_label),
                            modifier = Modifier,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray700),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = stringResource(
                            id = R.string.article_count_label,
                            data?.totalRead ?: 0
                        ),
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_badge_check),
                            contentDescription = "Navigation Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.read_done_label),
                            modifier = Modifier,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray700),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = stringResource(
                            id = R.string.article_count_label,
                            data?.readDone ?: 0
                        ),
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Navigation Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.reading_label),
                            modifier = Modifier,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray700),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = stringResource(
                            id = R.string.article_count_label,
                            data?.reading ?: 0
                        ),
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        repeat(2) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun LastRead(data: UiOverviewState.Success?, navController: NavHostController) {
    ContentTitleSection(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
        icon = painterResource(id = R.drawable.ic_eye),
        value = "กำลังอ่านล่าสุด (${data?.lastReadArticles?.size})",
        onClickedSeeMore = {

        }
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        data?.lastReadArticles?.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                ArticleVerticalItem(uiArticleVerticalItem, onClickSetting = {

                }) {
                    if (uiArticleVerticalItem.isPremium) {
                        //viewModel.onNavigateToReading(uiArticleVerticalItem.id)
                    } else {
                        navController.navigate(
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

@Composable
private fun ReadDone(data: UiOverviewState.Success?, navController: NavHostController) {
    ContentTitleSection(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
        icon = painterResource(id = R.drawable.ic_badge_check),
        value = "อ่านจบล่าสุด (${data?.readDoneArticles?.size})",
        onClickedSeeMore = {

        }
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        data?.readDoneArticles?.forEachIndexed { _, uiArticleVerticalItem ->
            item {
                ArticleVerticalItem(uiArticleVerticalItem, onClickSetting = {

                }) {
                    if (uiArticleVerticalItem.isPremium) {
                        //viewModel.onNavigateToReading(uiArticleVerticalItem.id)
                    } else {
                        navController.navigate(
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
