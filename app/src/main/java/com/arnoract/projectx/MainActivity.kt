package com.arnoract.projectx

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.home.view.MainScreen
import com.arnoract.projectx.ui.theme.ProjectXTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setStatusBarColor(this, Color.WHITE)
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