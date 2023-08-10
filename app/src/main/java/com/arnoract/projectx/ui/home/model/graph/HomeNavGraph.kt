package com.arnoract.projectx.ui.home.model.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arnoract.projectx.SubscriptionViewModel
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.article_set.ArticleSetScreen
import com.arnoract.projectx.ui.category.graph.categoryNavGraph
import com.arnoract.projectx.ui.category.view.CategoryScreen
import com.arnoract.projectx.ui.home.view.BottomBarScreen
import com.arnoract.projectx.ui.home.view.HomeScreen
import com.arnoract.projectx.ui.lesson.graph.lessonSentenceNavGraph
import com.arnoract.projectx.ui.profile.ProfileScreen
import com.arnoract.projectx.ui.reader.graph.readerNavGraph
import com.arnoract.projectx.ui.reading.view.ReadingScreen

@Composable
fun HomeNavGraph(navController: NavHostController, subscriptionViewModel: SubscriptionViewModel) {
    NavHost(
        route = GRAPH.MAIN,
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController, subscriptionViewModel)
        }
        composable(route = BottomBarScreen.Category.route) {
            CategoryScreen(navController)
        }
        composable(route = BottomBarScreen.Reading.route) {
            ReadingScreen(navController)
        }
        composable(route = BottomBarScreen.ArticleSet.route) {
            ArticleSetScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(subscriptionViewModel)
        }
        readerNavGraph(navController)
        lessonSentenceNavGraph(navController)
        categoryNavGraph(navController)
    }
}