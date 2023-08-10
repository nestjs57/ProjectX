package com.arnoract.projectx.ui.article_set

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.article_set.model.UiArticleSet
import com.arnoract.projectx.ui.article_set.model.UiArticleSetState
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.util.ColorUtil
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.ProgressView
import com.arnoract.projectx.ui.util.RequirePremiumDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun ArticleSetScreen(
    navController: NavHostController,
    mViewModel: ArticleSetViewModel = getViewModel()
) {
    val mState = mViewModel.uiArticleSetState.observeAsState()
    SubscribeEvent(navController, mViewModel)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_layout_fluid),
                    modifier = Modifier.size(21.dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.black))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.article_set_title_label),
                    modifier = Modifier,
                    fontSize = 22.sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            }

            when (val state = mState.value) {
                UiArticleSetState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                    }
                }

                is UiArticleSetState.Success -> {
                    LazyColumn {
                        items(state.data.size) {
                            ArticleSetItem(state.data[it])
                        }
                        item {
                            ArticleSetItemComingSoon()
                        }
                        item {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }
                }

                else -> {

                }
            }
        }
    }
}

@Composable
private fun ArticleSetItemComingSoon() {
    Box(
        modifier = Modifier
            .alpha(0.5f)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                color = colorResource(id = R.color.purple_500), shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "เซ็ตบทความใหม่เร็วๆนี้...",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ArticleSetItem(data: UiArticleSet) {
    val color = ColorUtil.hexToColor(data.articleSetBackground)
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                color = color, shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = data.articleSetName,
                        modifier = Modifier,
                        fontSize = 16.sp,
                        maxLines = 2,
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(
                            id = R.string.article_count_label,
                            data.articleCount
                        ),
                        modifier = Modifier,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.only_white),
                    )
                }
                val painter = rememberAsyncImagePainter(model = data.articleSetIcon)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ProgressView(
                percent = data.articleSetProgress,
                textProgress = "${data.articleSetProgress}%"
            )
        }
    }
}

@Composable
private fun SubscribeEvent(navHostController: NavHostController, viewModel: ArticleSetViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val openRequirePremiumDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    if (openRequirePremiumDialog.value) {
        Dialog(onDismissRequest = { openRequirePremiumDialog.value = false }) {
            RequirePremiumDialog(openDialogCustom = openRequirePremiumDialog) {
                openRequirePremiumDialog.value = false
                navHostController.navigate(BottomBarScreen.Profile.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}
