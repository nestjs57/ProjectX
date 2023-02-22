package com.arnoract.projectx.ui.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.view.NonLoginContent
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen() {
    val viewModel = getViewModel<ProfileViewModel>()

    val profileState by viewModel.profileState.observeAsState()
    when (val state: UiProfileState = profileState ?: return) {
        UiProfileState.Loading -> {

        }
        UiProfileState.NonLogin -> {
            NonLoginContent()
        }
        is UiProfileState.LoggedIn -> {
            Text(state.data.displayName)
        }
    }
}