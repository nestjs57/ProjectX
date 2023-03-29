package com.arnoract.projectx.ui.lesson.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.lesson.model.UiLessonSentenceDetailState
import com.arnoract.projectx.ui.lesson.viewmodel.LessonSentenceDetailViewModel
import com.arnoract.projectx.ui.util.CustomDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LessonSentenceDetail(id: String, navController: NavHostController) {

    val viewModel = getViewModel<LessonSentenceDetailViewModel>(parameters = { parametersOf(id) })

    SubscribeEvent(viewModel, navController)

    val uiState by viewModel.uiLessonSentenceDetailState.observeAsState()
    val context = LocalContext.current

    Column {
        Header(navController)
        when (val state = uiState) {
            UiLessonSentenceDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                }
            }
            is UiLessonSentenceDetailState.Success -> {
                LessonSentenceDetailContent(state.data) {
                    viewModel.onTextSpeech(context, it)
                }
            }
            null -> {}
        }
    }
}

@Composable
private fun Header(navController: NavHostController) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.white)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Menu Btn")
        }
        Text(
            text = "ย้อนกลับ",
            fontSize = 18.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SubscribeEvent(
    viewModel: LessonSentenceDetailViewModel, navController: NavHostController
) {
    val openDialog = remember { mutableStateOf(false) }

    val errorMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

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