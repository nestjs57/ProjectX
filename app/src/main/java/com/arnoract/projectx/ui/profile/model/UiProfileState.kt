package com.arnoract.projectx.ui.profile.model

sealed class UiProfileState {
    object Loading : UiProfileState()
    object NonLogin : UiProfileState()
    data class LoggedIn(val data: UiUser, val isSubscription: Boolean) : UiProfileState()
}
