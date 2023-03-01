package com.arnoract.projectx.ui.reader.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.reader.view.ReaderScreen

fun NavGraphBuilder.readerNavGraph(navController: NavHostController) {
    navigation(route = GRAPH.READER, startDestination = Route.readers) {
        composable(route = Route.readers) {
            val id = it.arguments?.getString("id") ?: ""
            ReaderScreen(id = id, navController)
        }
    }
}

