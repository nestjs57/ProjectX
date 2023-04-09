package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.SubscriptionViewModel
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.home.model.UiHomeState
import com.arnoract.projectx.ui.home.viewmodel.HomeViewModel
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.RequirePremiumDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, subscriptionViewModel: SubscriptionViewModel) {

    val viewModel = getViewModel<HomeViewModel>()

    SubscribeEvent(navHostController, viewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 56.dp, start = 16.dp, end = 16.dp)
            .background(colorResource(id = R.color.white))
    ) {
        val uiHomeState by viewModel.uiHomeState.observeAsState()
        when (val state: UiHomeState? = uiHomeState) {
            is UiHomeState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiHomeState.Success -> {
                HomeContent(
                    viewModel,
                    navHostController,
                    state.comingSoonItem,
                    state.recommendedItem,
                    state.recentlyPublished,
                )
            }
            else -> {

            }
        }
    }
}

@Composable
private fun SubscribeEvent(navHostController: NavHostController, viewModel: HomeViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val openRequirePremiumDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    OnEvent(event = viewModel.showDialogErrorNoPremium, onEvent = {
        openRequirePremiumDialog.value = true
    })

    OnEvent(event = viewModel.navigateToReader, onEvent = {
        navHostController.navigate(
            Route.readers.replace(
                "{id}", it
            )
        )
    })

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