package com.arnoract.projectx.ui.profile

import android.content.Context
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
import androidx.compose.ui.window.DialogProperties
import com.arnoract.projectx.R
import com.arnoract.projectx.SubscriptionViewModel
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.base.getActivity
import com.arnoract.projectx.ui.profile.model.UiProfileState
import com.arnoract.projectx.ui.profile.view.LoggedInContent
import com.arnoract.projectx.ui.profile.view.NonLoginContent
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.GetRewardSuccessDialog
import com.arnoract.projectx.ui.util.LoadingAdsDialog
import com.arnoract.projectx.ui.util.SubscriptionSuccessDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import org.koin.androidx.compose.getViewModel

private var rewardedAd: RewardedAd? = null
private var adRequest: AdRequest? = null
private var isEnableShowAds: Boolean = true

@Composable
fun ProfileScreen(subscriptionViewModel: SubscriptionViewModel) {
    val viewModel = getViewModel<ProfileViewModel>()

    val mContext = LocalContext.current

    val profileState by viewModel.profileState.observeAsState()
    when (val state: UiProfileState = profileState ?: return) {
        UiProfileState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
            }
        }
        UiProfileState.NonLogin -> {
            NonLoginContent {
                viewModel.signInWithGoogleToken(it)
            }
        }
        is UiProfileState.LoggedIn -> {
            subscriptionViewModel.refreshIsSubscribed()
            LoggedInContent(
                state.data,
                subscriptionViewModel.isSubscribed.observeAsState(),
                onClickedSignOut = {
                    viewModel.signOutWithGoogle()
                }) {
                subscriptionViewModel.openPaywall(mContext.getActivity()!!)
            }
        }
    }
    SubscribeEvent(subscriptionViewModel, viewModel)
}

@Composable
fun SubscribeEvent(subscriptionViewModel: SubscriptionViewModel, viewModel: ProfileViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val openDialogLoading = remember { mutableStateOf(false) }
    val openDialogGetGoldCoinSuccess = remember { mutableStateOf(false) }
    val openDialogSubscriptionSuccess = remember { mutableStateOf(false) }

    val errorMessage = remember { mutableStateOf("") }
    val currentReward = remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current

    OnEvent(event = subscriptionViewModel.subscribedSuccess, onEvent = {
        openDialogSubscriptionSuccess.value = true
    })

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    OnEvent(event = viewModel.loadAds) {
        loadAds(context, viewModel)
    }

    OnEvent(event = viewModel.showDialogGetReward) {
        currentReward.value = it
        openDialogGetGoldCoinSuccess.value = true
    }

    OnEvent(event = viewModel.isShowDialogLoading, onEvent = {
        openDialogLoading.value = it
    })

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }

    if (openDialogSubscriptionSuccess.value) {
        Dialog(onDismissRequest = { openDialogGetGoldCoinSuccess.value = false }) {
            SubscriptionSuccessDialog(
                openDialogCustom = openDialogSubscriptionSuccess
            )
        }
    }

    if (openDialogGetGoldCoinSuccess.value) {
        Dialog(onDismissRequest = { openDialogGetGoldCoinSuccess.value = false }) {
            GetRewardSuccessDialog(
                currentReward.value,
                openDialogCustom = openDialogGetGoldCoinSuccess
            )
        }
    }

    if (openDialogLoading.value) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ), onDismissRequest = {
                openDialogLoading.value = false
            }) {
            LoadingAdsDialog(openDialogCustom = openDialogLoading) {
                isEnableShowAds = false
            }
        }
    }
}

private fun loadAds(content: Context, viewModel: ProfileViewModel) {
    isEnableShowAds = true
    adRequest = AdRequest.Builder().build()
    viewModel.setIsShowDialogLoading(true)
    RewardedAd.load(content,
        "ca-app-pub-9170460661148665/2980058922",
        adRequest!!,
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
                viewModel.setIsShowDialogLoading(false)
                viewModel.setErrorDialog(content.getString(R.string.no_ads_label))
            }

            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
                viewModel.setIsShowDialogLoading(false)
                if (isEnableShowAds) {
                    rewardedAd?.let { ad ->
                        ad.show(content.getActivity()!!) { rewardItem ->
                            val rewardAmount = rewardItem.amount
                            viewModel.onShowDialogGetRewardItem(rewardAmount)
                        }
                    } ?: run {
                        viewModel.setErrorDialog(content.getString(R.string.fail_to_show_ads_label))
                    }
                }
            }
        })
}