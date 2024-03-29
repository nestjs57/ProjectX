package com.arnoract.projectx.ui.reading.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.reading.model.UiReadingArticleState
import com.arnoract.projectx.ui.reading.model.UiReadingFilter
import com.arnoract.projectx.ui.reading.viewmodel.ReadingViewModel
import com.arnoract.projectx.ui.util.BaseTwoButtonDialog
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.ExampleFeatureDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun ReadingScreen(navController: NavHostController) {
    val viewModel = getViewModel<ReadingViewModel>()

    val uiState by viewModel.uiReadingState.observeAsState()
    val filter by viewModel.uiReadingFilter.observeAsState()

    var isShow by remember {
        mutableStateOf(false)
    }

    if (isShow) {
        BottomDialogFilterReadingStatus(filter, onClickedDismiss = {
            isShow = false
        }, onClickedFilter = {
            viewModel.setFilter(it)
        })
    }

    SubscribeEvent(viewModel)

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.filter_label), fontSize = 18.sp
            )
            Text(text = " : ", fontSize = 18.sp)
            Text(
                text = when (filter) {
                    UiReadingFilter.TOTAL -> stringResource(id = R.string.total_label)
                    UiReadingFilter.COMPLETE -> stringResource(id = R.string.only_read_complete_label)
                    UiReadingFilter.READING -> stringResource(id = R.string.only_reading_label)
                    else -> stringResource(id = R.string.total_label)
                }, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f)
            )
            val isSyncing = viewModel.sycing.observeAsState()
            if (isSyncing.value == true) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.purple_500), modifier = Modifier.size(30.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_cloud_download),
                    modifier = Modifier
                        .size(23.dp)
                        .clickable {
                            viewModel.onSyncDataReadingUseCase()
                        },
                    contentDescription = null,
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.black))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_filter),
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        isShow = true
                    },
                contentDescription = null,
                alignment = Alignment.Center
            )
        }
        when (val state = uiState) {
            is UiReadingArticleState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiReadingArticleState.Success -> {
                ReadingContent(state.data, navController, viewModel)
            }
            is UiReadingArticleState.NonLogin -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_freelancer_amico),
                            modifier = Modifier.size(230.dp),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.desc_dialog_require_login_label),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier
                            .height(48.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                navController.navigate(BottomBarScreen.Profile.route) {
                                    launchSingleTop = true
                                }
                            }
                            .background(colorResource(id = R.color.purple_500)),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(id = R.string.go_to_profile_page_label),
                                modifier = Modifier,
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.white),
                            )
                        }
                    }
                }
            }
            is UiReadingArticleState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kids_reading_amico),
                            modifier = Modifier.size(230.dp),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.empty_reading_label),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            is UiReadingArticleState.NoSubscription -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kids_reading_amico),
                            modifier = Modifier.size(230.dp),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.reading_page_non_subs_label),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier.clickable {
                                viewModel.onOpenDialogDemoFeature()
                            },
                            text = stringResource(id = R.string.example_label), fontSize = 18.sp,
                            textDecoration = TextDecoration.Underline,
                            color = colorResource(id = R.color.purple_500)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier
                            .height(48.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                navController.navigate(BottomBarScreen.Profile.route) {
                                    launchSingleTop = true
                                }
                            }
                            .background(colorResource(id = R.color.purple_500)),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(id = R.string.go_to_profile_page_label),
                                modifier = Modifier,
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.white),
                            )
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun SubscribeEvent(viewModel: ReadingViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val openDialogDemoFeature = remember { mutableStateOf(false) }
    val openDialogSetting = remember { mutableStateOf(false) }
    val openDialogConfirmDelete = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val articleSelectedSetting = remember {
        mutableStateOf<UiArticleVerticalItem?>(null)
    }

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    OnEvent(event = viewModel.openDialogDemoFeature, onEvent = {
        openDialogDemoFeature.value = true
    })

    OnEvent(event = viewModel.openDialogSetting, onEvent = {
        articleSelectedSetting.value = it
        openDialogSetting.value = true
    })

    OnEvent(event = viewModel.openDialogConfirmRemove, onEvent = {
        openDialogConfirmDelete.value = true
    })

    if (openDialogConfirmDelete.value) {
        Dialog(onDismissRequest = {
            openDialogConfirmDelete.value = false
        }) {
            BaseTwoButtonDialog(
                title = stringResource(id = R.string.confirm_delete_label),
                description = stringResource(
                    id = R.string.confirm_delete_with_label,
                    "'${articleSelectedSetting.value?.titleTh ?: ""}'"
                ),
                onClickLeftButton = {
                    openDialogConfirmDelete.value = false
                },
                onClickRightButton = {
                    openDialogSetting.value = false
                    openDialogConfirmDelete.value = false
                    viewModel.deleteFromReading(articleSelectedSetting.value?.id ?: "")
                }
            )
        }
    }

    if (openDialogSetting.value) {
        BottomDialogArticleSetting(
            title = articleSelectedSetting.value?.titleTh ?: "",
            id = articleSelectedSetting.value?.id ?: "",
            onClickedDismiss = {
                openDialogSetting.value = false
            },
            onClickedRemoveArticle = {
                viewModel.openDialogConfirmDelete()
            })
    }

    if (openDialogDemoFeature.value) {
        Dialog(onDismissRequest = { openDialogDemoFeature.value = false }) {
            ExampleFeatureDialog(
                title = stringResource(R.string.reading_label),
                description = stringResource(id = R.string.reading_not_conflict_label),
                imageDrawable = R.drawable.demo_reading,
                openDialogCustom = openDialogDemoFeature
            )
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}