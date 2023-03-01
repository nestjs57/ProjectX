package com.arnoract.projectx.ui.reader.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.arnoract.projectx.ui.reader.model.UiReaderState
import com.arnoract.projectx.ui.reader.viewmodel.ReaderViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReaderScreen(id: String, navController: NavHostController) {
    val viewModel = getViewModel<ReaderViewModel>(
        parameters = { parametersOf(id) }
    )
    val uiState by viewModel.uiReaderState.observeAsState()
    val context = LocalContext.current
    when (val state: UiReaderState? = uiState) {
        UiReaderState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiReaderState.Success -> {
            ReaderContent(
                titleTh = state.titleTh,
                titleEn = state.titleEn,
                uiParagraph = state.uiParagraph,
                currentParagraphSelected = state.currentParagraphSelected,
                onClickedSelectVocabulary = {
                    viewModel.setSelectedParagraph(it)
                },
                onClickedNextParagraph = {
                    viewModel.onClickedNextParagraph()
                },
                onClickedPreviousParagraph = {
                    viewModel.onClickedPreviousParagraph()
                },
                onClickedBack = {
                    navController.popBackStack()
                },
                onTextSpeech = {
                    viewModel.onTextSpeech(context, it)
                }
            )
        }
        else -> {

        }
    }
}