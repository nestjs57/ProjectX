package com.arnoract.projectx.ui.home.model.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.home.view.HomeScreen
import com.arnoract.projectx.ui.profile.ProfileScreen
import com.arnoract.projectx.ui.reader.graph.readerNavGraph

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        route = GRAPH.MAIN,
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {

        }
        composable(route = BottomBarScreen.Settings.route) {
            ProfileScreen()
        }
        readerNavGraph(navController)
    }
}