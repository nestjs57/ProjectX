package com.arnoract.projectx.ui.lesson.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceState
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceViewModel
import com.arnoract.projectx.ui.util.ConfirmPurchaseLessonDialog
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.NotEnoughCoinDialog
import com.arnoract.projectx.ui.util.RequireLoginDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun LessonSentenceScreen(navController: NavHostController) {

    val viewModel = getViewModel<LessonSentenceViewModel>()

    SubscribeEvent(viewModel, navController)

    val uiState by viewModel.uiLessonState.observeAsState()

    when (val state = uiState) {
        UiLessonSentenceState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
            }
        }
        is UiLessonSentenceState.Success -> {
            LessonSentenceContent(
                state.comingSoon,
                state.recentlyPublished,
                navController,
                viewModel
            )
        }
        else -> {}
    }
}

@Composable
private fun SubscribeEvent(viewModel: LessonSentenceViewModel, navController: NavHostController) {
    val openDialog = remember { mutableStateOf(false) }
    val openDialogRequireLogin = remember { mutableStateOf(false) }
    val openDialogNotEnoughCoin = remember { mutableStateOf(false) }
    val openDialogConfirmPurchase =
        remember { mutableStateOf<ConfirmPurchaseState>(ConfirmPurchaseState.Hide) }

    val errorMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    OnEvent(event = viewModel.showDialogRequireLogin, onEvent = {
        openDialogRequireLogin.value = true
    })

    OnEvent(event = viewModel.showDialogConfirmPurchase, onEvent = {
        openDialogConfirmPurchase.value = it
    })

    OnEvent(event = viewModel.navigateToLessonSentenceDetailEvent, onEvent = {
        navController.navigate(
            Route.lessonSentence.replace(
                "{id}",
                it
            )
        )
    })

    OnEvent(event = viewModel.accessContentWithCoinSuccessEvent, onEvent = {
        Toast.makeText(
            context,
            "${context.getString(R.string.use_label)} ${it.first} ${context.getString(R.string.gold_coin_label)} ${
                context.getString(
                    R.string.remaining_label
                )
            } ${it.second} ${context.getString(R.string.coin_label)}",
            Toast.LENGTH_LONG
        ).show()
    })

    OnEvent(event = viewModel.notEnoughCoinEvent, onEvent = {
        openDialogNotEnoughCoin.value = true
    })

    when (val state = openDialogConfirmPurchase.value) {
        is ConfirmPurchaseState.Show -> {
            Dialog(onDismissRequest = {
                openDialogConfirmPurchase.value = ConfirmPurchaseState.Hide

            }) {
                ConfirmPurchaseLessonDialog(
                    openDialogState = openDialogConfirmPurchase,
                    onClickedConfirm = {
                        val data = openDialogConfirmPurchase.value as ConfirmPurchaseState.Show
                        viewModel.accessContentWithCoin(data.data)
                        openDialogConfirmPurchase.value = ConfirmPurchaseState.Hide
                    }
                )
            }
        }
        else -> {}
    }

    if (openDialogNotEnoughCoin.value) {
        Dialog(onDismissRequest = { openDialogNotEnoughCoin.value = false }) {
            NotEnoughCoinDialog(openDialogCustom = openDialogNotEnoughCoin) {
                openDialogNotEnoughCoin.value = false
                navController.navigate(BottomBarScreen.Profile.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    if (openDialogRequireLogin.value) {
        Dialog(onDismissRequest = { openDialogRequireLogin.value = false }) {
            RequireLoginDialog(openDialogCustom = openDialogRequireLogin) {
                openDialogRequireLogin.value = false
                navController.navigate(BottomBarScreen.Profile.route) {
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