package com.arnoract.projectx.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.exception.UnAuthorizeException
import com.arnoract.projectx.domain.usecase.profile.GetProfileUseCase
import com.arnoract.projectx.domain.usecase.profile.LoginWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.SignOutWithGoogleUseCase
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.model.mapper.UserToUiUserMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val signOutWithGoogleUseCase: SignOutWithGoogleUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _profileState = MutableLiveData<UiProfileState?>()
    val profileState: LiveData<UiProfileState?>
        get() = _profileState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            _profileState.value = UiProfileState.Loading
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getProfileUseCase.invoke(Unit).successOrThrow()
                }
                _profileState.value = UiProfileState.LoggedIn(UserToUiUserMapper.map(result))
            } catch (e: UnAuthorizeException) {
                _profileState.value = UiProfileState.NonLogin
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun signInWithGoogleToken(token: String) {
        _profileState.value = UiProfileState.Loading
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    loginWithGoogleUseCase.invoke(token).successOrThrow()
                }
                getProfile()
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun signOutWithGoogle() {
        _profileState.value = UiProfileState.Loading
        viewModelScope.launch {
            delay(1000)
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    signOutWithGoogleUseCase.invoke(Unit).successOrThrow()
                }
                getProfile()
            } catch (e: Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }
}