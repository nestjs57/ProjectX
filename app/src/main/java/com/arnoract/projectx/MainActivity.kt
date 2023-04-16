package com.arnoract.projectx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.home.view.MainScreen
import com.arnoract.projectx.ui.theme.ProjectXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mSubscriptionViewModel: SubscriptionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        mSubscriptionViewModel.initBilling(this)
        MobileAds.initialize(this)

        setContent {
            MobileAds.initialize(this) {}
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = colorResource(id = R.color.background_dark_mode), darkIcons = false
            )

            ProjectXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        route = GRAPH.ROOT,
                        navController = rememberNavController(),
                        startDestination = GRAPH.MAIN
                    ) {
                        composable(GRAPH.MAIN) {
                            MainScreen(mSubscriptionViewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSubscriptionViewModel.endConnection()
    }
}