package com.arnoract.projectx.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.successOrThrow
import com.arnoract.projectx.domain.exception.UnAuthorizeException
import com.arnoract.projectx.domain.model.profile.User
import com.arnoract.projectx.domain.usecase.profile.GetProfileUseCase
import com.arnoract.projectx.domain.usecase.profile.LoginWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.SignOutWithGoogleUseCase
import com.arnoract.projectx.domain.usecase.profile.UpdateGoldCoinUseCase
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.model.mapper.UserToUiUserMapper
import com.arnoract.projectx.util.setValueIfNew
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val signOutWithGoogleUseCase: SignOutWithGoogleUseCase,
    private val updateGoldCoinUseCase: UpdateGoldCoinUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _profileState = MutableLiveData<UiProfileState?>()
    val profileState: LiveData<UiProfileState?>
        get() = _profileState

    private val _error = MutableSharedFlow<String>()
    val error: MutableSharedFlow<String>
        get() = _error

    private val _loadADs = MutableSharedFlow<Unit>()
    val loadAds: MutableSharedFlow<Unit>
        get() = _loadADs

    private val _isShowDialogLoading = MutableSharedFlow<Boolean>()
    val isShowDialogLoading: MutableSharedFlow<Boolean>
        get() = _isShowDialogLoading

    private val _showDialogGetReward = MutableSharedFlow<Int>()
    val showDialogGetReward: MutableSharedFlow<Int>
        get() = _showDialogGetReward

    private val _showDialogPaywallSubscription = MutableSharedFlow<Unit>()
    val showDialogPaywallSubscription: MutableSharedFlow<Unit>
        get() = _showDialogPaywallSubscription

    private var _user = MutableLiveData<User>()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            _profileState.setValueIfNew(UiProfileState.Loading)
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getProfileUseCase.invoke(Unit).successOrThrow()
                }
                _user.value = result
                _profileState.setValueIfNew(UiProfileState.LoggedIn(UserToUiUserMapper.map(_user.value)))
            } catch (e: UnAuthorizeException) {
                _profileState.setValueIfNew(UiProfileState.NonLogin)
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

    fun onClickedGetGoldCoin() {
        viewModelScope.launch {
            _loadADs.emit(Unit)
        }
    }

    fun setIsShowDialogLoading(isShow: Boolean) {
        viewModelScope.launch {
            _isShowDialogLoading.emit(isShow)
        }
    }

    fun setErrorDialog(error: String) {
        viewModelScope.launch {
            _error.emit(error)
        }
    }

    fun onShowDialogGetRewardItem(rewardAmount: Int) {
        viewModelScope.launch {
            updateGoldCoin(rewardAmount)
            _showDialogGetReward.emit(rewardAmount)
        }
    }

    private fun updateGoldCoin(reward: Int) {
        viewModelScope.launch {
            try {
                val result = withContext(coroutinesDispatcherProvider.io) {
                    updateGoldCoinUseCase.invoke(reward).successOrThrow()
                }
                _user.value = _user.value?.copy(coin = result)
                _profileState.value = UiProfileState.LoggedIn(UserToUiUserMapper.map(_user.value))
            } catch (e: java.lang.Exception) {
                _error.emit(e.message ?: "Unknown Error.")
            }
        }
    }

    fun onOpenPaywallSubscription() {
        viewModelScope.launch {
            _showDialogPaywallSubscription.emit(Unit)
        }
    }
}