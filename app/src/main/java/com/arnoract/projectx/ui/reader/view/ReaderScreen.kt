package com.arnoract.projectx.ui.reader.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getBackgroundColor
import com.arnoract.projectx.ui.reader.model.UiParagraph
import com.arnoract.projectx.ui.reader.model.UiReaderState
import com.arnoract.projectx.ui.reader.viewmodel.ReaderViewModel
import com.arnoract.projectx.ui.util.CustomDialog
import com.arnoract.projectx.ui.util.DescriptionBottomSheetDialog
import com.arnoract.projectx.ui.util.ExamVocabularyBottomSheetDialog
import com.arnoract.projectx.ui.util.StructureSentenceDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReaderScreen(id: String, navController: NavHostController) {
    val viewModel = getViewModel<ReaderViewModel>(
        parameters = { parametersOf(id) }
    )
    val uiState by viewModel.uiReaderState.observeAsState()
    val setting by viewModel.readerSetting.observeAsState()

    SubscribeEvent(viewModel, navController)

    BackHandler(enabled = true) {
        viewModel.onClickedBack()
    }

    val context = LocalContext.current
    when (val state: UiReaderState? = uiState) {
        UiReaderState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getBackgroundColor(setting?.backgroundMode)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
            }
        }
        is UiReaderState.Success -> {
            ReaderContent(
                titleTh = state.titleTh,
                titleEn = state.titleEn,
                uiParagraph = state.uiParagraph,
                readerSetting = setting,
                currentParagraphSelected = state.currentParagraphSelected,
                uiTranSlateParagraph = state.uiTranSlateParagraph,
                uiContentHTML = state.contentRawStateHTML,
                isSubscription = state.isSubscription,
                isLogin = state.isLogin,
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
                    viewModel.onClickedBack()
                },
                onTextSpeech = {
                    viewModel.onTextSpeech(context, it)
                },
                onClickedTextSize = {
                    viewModel.setFontSizeSetting(it)
                },
                onClickedBackgroundModel = {
                    viewModel.setBackgroundModeSetting(it)
                },
                onClickedStructureSentence = {
                    viewModel.onOpenWenView(it)
                },
                onClickedAutoSpeak = {

                },
                onClickedDetailVocabularyMode = {
                    viewModel.onOpenVocabularyMode()
                },
                onToggleModeVocabulary = {
                    viewModel.onToggleModeVocabulary(it)
                }
            )
        }
        else -> {

        }
    }
}

@Composable
private fun SubscribeEvent(viewModel: ReaderViewModel, navController: NavHostController) {
    val openDialog = remember { mutableStateOf(false) }
    val openStructureSentenceDialog = remember { mutableStateOf(false) }
    val openDescriptionDialog = remember { mutableStateOf(false) }
    val openDialogExamVocabulary = remember {
        mutableStateOf(false)
    }

    val errorMessage = remember { mutableStateOf("") }
    val uiParagraph = remember { mutableStateOf<List<UiParagraph>>(listOf()) }
    val openWebViewUrl = remember {
        mutableStateOf("")
    }

    OnEvent(event = viewModel.openDetailVocabularyModeEvent, onEvent = {
        openDescriptionDialog.value = true
    })

    OnEvent(event = viewModel.error, onEvent = {
        openDialog.value = true
        errorMessage.value = it
    })

    OnEvent(event = viewModel.openWebView, onEvent = {
        openStructureSentenceDialog.value = true
        openWebViewUrl.value = it
    })

    OnEvent(event = viewModel.openDialogExamVocabulary, onEvent = {
        openDialogExamVocabulary.value = true
        uiParagraph.value = it
    })

    if (openDialogExamVocabulary.value) {
        ExamVocabularyBottomSheetDialog(
            uiParagraph.value,
            viewModel.readerSetting.value,
            onClickedDismiss = {
                openDialogExamVocabulary.value = false
            },
            onNextParagraph = {
                openDialogExamVocabulary.value = false
                viewModel.onNextParagraph()
            }
        )
    }

    OnEvent(event = viewModel.clickedBackEvent, onEvent = {
        navController.popBackStack()
    })

    if (openDescriptionDialog.value) {
        DescriptionBottomSheetDialog(
            stringResource(id = R.string.mode_exam_vocab_label),
            stringResource(id = R.string.stat_and_reading_of_you_detail_label),
            viewModel.readerSetting.value
        ) {
            openDescriptionDialog.value = false
        }
    }

    if (openStructureSentenceDialog.value) {
        StructureSentenceDialog(
            viewModel.readerSetting.value,
            openWebViewUrl.value
        ) {
            openStructureSentenceDialog.value = false
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            CustomDialog(openDialogCustom = openDialog, description = errorMessage.value)
        }
    }
}