package com.arnoract.projectx.ui.reading.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.home.view.ArticleVerticalItem
import com.arnoract.projectx.ui.reading.viewmodel.ReadingViewModel

@Composable
fun ReadingContent(
    data: List<UiArticleVerticalItem>, navController: NavHostController, viewModel: ReadingViewModel
) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(data.size) {
            Row {
                ArticleVerticalItem(
                    model = data[it],
                    isSupportGridLayout = true,
                    onClickSetting = {
                        viewModel.openDialogSetting(data[it])
                    }) {
                    navController.navigate(
                        Route.readers.replace(
                            "{id}", data[it].id
                        )
                    )
                }
            }
        }
    }
}