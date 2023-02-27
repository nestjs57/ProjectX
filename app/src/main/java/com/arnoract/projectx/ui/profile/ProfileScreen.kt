package com.arnoract.projectx.ui.profile

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
import androidx.compose.ui.window.Dialog
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.view.LoggedInContent
import com.arnoract.projectx.ui.profile.view.NonLoginContent
import com.arnoract.projectx.ui.util.CustomDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen() {
    val viewModel = getViewModel<ProfileViewModel>()

    val profileState by viewModel.profileState.observeAsState()
    when (val state: UiProfileState = profileState ?: return) {
        UiProfileState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        UiProfileState.NonLogin -> {
            NonLoginContent {
                viewModel.signInWithGoogleToken(it)
            }
        }
        is UiProfileState.LoggedIn -> {
            LoggedInContent(state.data, onClickedSignOut = {
                viewModel.signOutWithGoogle()
            })
        }
    }
    SubscribeEvent(viewModel)
}

@Composable
fun SubscribeEvent(viewModel: ProfileViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}