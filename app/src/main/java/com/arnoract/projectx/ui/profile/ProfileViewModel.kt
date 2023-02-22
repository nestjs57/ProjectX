package com.arnoract.projectx.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.exception.UnAuthorizeException
import com.arnoract.projectx.domain.usecase.profile.GetProfileUseCase
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.model.mapper.UserToUiUserMapper
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _profileState = MutableLiveData<UiProfileState?>()
    val profileState: LiveData<UiProfileState?>
        get() = _profileState

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

            }
        }
    }


    fun signInWithGoogleToken(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        signWithCredential(credential)
    }

    private fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    print("")
                }
        } catch (e: Exception) {
//            _error.value = e.localizedMessage ?: "Unknown error"
            print("")
        }
    }
}