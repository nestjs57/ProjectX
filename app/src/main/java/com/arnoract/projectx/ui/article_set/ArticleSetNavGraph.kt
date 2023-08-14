package com.arnoract.projectx.ui.article_set

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.GRAPH

fun NavGraphBuilder.articleSetNavGraph(navController: NavHostController) {
    navigation(route = GRAPH.ARTICLE_SET, startDestination = Route.article_set_detail) {
        composable(route = Route.article_set_detail) {
            val id = it.arguments?.getString("articleSetId") ?: ""
            ArticleSetDetailScreen(id = id, navController)
        }
    }
}

