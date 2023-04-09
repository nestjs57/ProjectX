package com.arnoract.projectx.ui.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arnoract.projectx.R
import com.arnoract.projectx.SubscriptionViewModel
import com.arnoract.projectx.ui.home.model.graph.HomeNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    subscriptionViewModel: SubscriptionViewModel,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavGraph(navController = navController, subscriptionViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Category,
        BottomBarScreen.Reading,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = colorResource(R.color.white)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            when (screen) {
                BottomBarScreen.Home -> {
                    Icon(
                        painterResource(id = R.drawable.ic_home),
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
                BottomBarScreen.Category -> {
                    Icon(
                        painterResource(id = R.drawable.ic_search_mini),
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
                BottomBarScreen.Reading -> {
                    Icon(
                        painterResource(id = R.drawable.ic_auto_stories),
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
                BottomBarScreen.Profile -> {
                    Icon(
                        painterResource(id = R.drawable.ic_profile_with_heart),
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = colorResource(id = R.color.black),
        unselectedContentColor = colorResource(id = R.color.gray700),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })
}