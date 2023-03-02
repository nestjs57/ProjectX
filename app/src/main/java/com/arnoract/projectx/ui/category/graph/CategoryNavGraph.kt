package com.arnoract.projectx.ui.category.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.category.view.CategoryDetailScreen
import com.arnoract.projectx.ui.reader.view.ReaderScreen


fun NavGraphBuilder.categoryNavGraph(navController: NavHostController) {
    navigation(route = GRAPH.CATEGORY, startDestination = Route.category_detail) {
        composable(route = Route.category_detail) {
            val id = it.arguments?.getString("categoryId") ?: ""
            CategoryDetailScreen(id, navController)
        }
    }
}