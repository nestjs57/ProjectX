package com.arnoract.projectx.ui.article_set

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arnoract.projectx.R
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.article_set.model.UiArticleSetDetail
import com.arnoract.projectx.ui.article_set.model.UiArticleSetDetailState
import com.arnoract.projectx.ui.home.model.mapper.UiArticleCategoryToCategoryLabelMapper
import com.arnoract.projectx.ui.util.ColorUtil
import com.arnoract.projectx.ui.util.ProgressView
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleSetDetailScreen(
    id: String,
    navController: NavHostController,
    mViewModel: ArticleSetDetailViewModel = getViewModel {
        parametersOf(id)
    }
) {
    val uiState = mViewModel.uiArticleSetDetailState.observeAsState()
    val color: Color =
        if (mViewModel.toolbarColor.observeAsState().value == null) colorResource(id = R.color.white) else ColorUtil.hexToColor(
            mViewModel.toolbarColor.observeAsState().value!!
        )

    Column {
        ToolBar(mViewModel, onClickedBack = {
            navController.popBackStack()
        })
        Box(
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .alpha(0.3f)
                    .background(color)
            )
            Column(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                when (val state = uiState.value) {
                    UiArticleSetDetailState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                        }
                    }

                    is UiArticleSetDetailState.Success -> {
                        Text(
                            text = stringResource(id = R.string.article_set_total_in_set_title_label),
                            modifier = Modifier.padding(top = 8.dp, end = 16.dp),
                            fontSize = 22.sp,
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold
                        )
                        LazyColumn(content = {
                            repeat(state.data.size) {
                                val data = state.data[it]
                                item {
                                    ArticleSetDetailItem(data, onClickedItem = { articleId ->
                                        navController.navigate(
                                            Route.readers.replace(
                                                "{id}",
                                                articleId
                                            )
                                        )
                                    })
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        })
                    }

                    else -> {

                    }
                }
            }
        }
    }
}

@Composable
private fun ToolBar(
    viewModel: ArticleSetDetailViewModel,
    onClickedBack: () -> Unit,
) {
    val color: Color =
        if (viewModel.toolbarColor.observeAsState().value == null) colorResource(id = R.color.white) else ColorUtil.hexToColor(
            viewModel.toolbarColor.observeAsState().value!!
        )
    val isLoadSuccess =
        viewModel.uiArticleSetDetailState.observeAsState().value is UiArticleSetDetailState.Success
    Row(
        modifier = Modifier
            .height(56.dp)
            .background(color),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickedBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Menu Btn",
                tint = colorResource(id = if (isLoadSuccess) R.color.white else R.color.black)
            )
        }
        Text(
            text = stringResource(id = R.string.back_label),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = colorResource(id = if (isLoadSuccess) R.color.white else R.color.black)
        )
    }
}

@Composable
private fun ArticleSetDetailItem(data: UiArticleSetDetail, onClickedItem: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    onClickedItem(data.id)
                },
            ),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        val link = data.articleCover
        val mWidth = 75
        val mHeight = mWidth.times(1.50)
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(link).crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(mWidth.dp)
                    .height(mHeight.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.gray300))
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = data.articleName,
                    modifier = Modifier,
                    maxLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = "หมวดหมู่ : ${
                        stringResource(
                            id = UiArticleCategoryToCategoryLabelMapper.map(
                                data.articleCategory
                            )
                        )
                    }",
                    maxLines = 2,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = data.articleDescription,
                    Modifier.alpha(0.5f),
                    maxLines = 2,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                ProgressView(
                    percent = data.percentProgress,
                    textProgress = "${data.currentReadParagraph}/${data.totalParagraphArticle}"
                )
            }
        }
    }
}