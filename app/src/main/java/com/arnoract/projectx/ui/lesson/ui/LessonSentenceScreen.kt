package com.arnoract.projectx.ui.lesson.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceState
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceViewModel
import com.arnoract.projectx.ui.util.CustomDialog
import org.koin.androidx.compose.getViewModel

@Composable
fun LessonSentenceScreen(navController: NavHostController) {

    val viewModel = getViewModel<LessonSentenceViewModel>()

    SubscribeEvent(viewModel)

    val uiState by viewModel.uiLessonState.observeAsState()

    when (val state = uiState) {
        UiLessonSentenceState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
            }
        }
        is UiLessonSentenceState.Success -> {
            LessonSentenceContent(state.comingSoon, state.recentlyPublished, navController)
        }
        else -> {}
    }
}

@Composable
private fun SubscribeEvent(viewModel: LessonSentenceViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}