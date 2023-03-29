package com.arnoract.projectx

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.home.view.MainScreen
import com.arnoract.projectx.ui.theme.ProjectXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


class MainActivity : ComponentActivity() {

    private var rewardedAd: RewardedAd? = null
    private var adRequest: AdRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

//        adRequest = AdRequest.Builder().build().apply {
//            //isTestDevice(this@MainActivity)
//        }
//
//        loadAds()

        setContent {
            MobileAds.initialize(this) {}
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = colorResource(id = R.color.background_dark_mode),
                darkIcons = false
            )

            ProjectXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        route = GRAPH.ROOT,
                        navController = rememberNavController(),
                        startDestination = GRAPH.MAIN
                    ) {
                        composable(GRAPH.MAIN) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }

    private fun loadAds() {
        RewardedAd.load(
            this,
            "ca-app-pub-9170460661148665/2980058922",
            adRequest!!,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    Toast.makeText(
                        this@MainActivity,
                        "${adError.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Toast.makeText(
                        this@MainActivity,
                        "onAdLoaded",
                        Toast.LENGTH_LONG
                    ).show()

                    rewardedAd?.let { ad ->
                        ad.show(this@MainActivity, OnUserEarnedRewardListener { rewardItem ->
                            // Handle the reward.
                            val rewardAmount = rewardItem.amount
                            val rewardType = rewardItem.type
                            Toast.makeText(
                                this@MainActivity,
                                "rewardItem",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    } ?: run {
                        Toast.makeText(
                            this@MainActivity,
                            "showed failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }
}

fun setStatusBarColor(activity: Activity, color: Int) {
    activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    activity.window.statusBarColor = color
    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ProjectXTheme {
        MainScreen()
    }
}