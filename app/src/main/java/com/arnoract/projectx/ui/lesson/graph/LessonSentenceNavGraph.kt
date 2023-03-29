package com.arnoract.projectx.ui.lesson.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arnoract.projectx.base.Route
import com.arnoract.projectx.ui.GRAPH
import com.arnoract.projectx.ui.lesson.ui.LessonSentenceDetail

fun NavGraphBuilder.lessonSentenceNavGraph(navController: NavHostController) {
    navigation(route = GRAPH.LESSON_SENTENCE, startDestination = Route.lessonSentence) {
        composable(route = Route.lessonSentence) {
            val id = it.arguments?.getString("id") ?: ""
            LessonSentenceDetail(id = id, navController)
        }
    }
}