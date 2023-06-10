package com.arnoract.projectx.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.arnoract.projectx.R
import com.arnoract.projectx.base.OnEvent
import com.arnoract.projectx.ui.reader.ReaderSettingUtil
import com.arnoract.projectx.ui.reader.model.ReaderSetting
import com.arnoract.projectx.ui.reader.model.UiParagraph
import com.arnoract.projectx.ui.reader.viewmodel.ExamVocabularyViewModel
import com.arnoract.projectx.ui.reader.viewmodel.UiCorrectState
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import org.koin.androidx.compose.getViewModel

@Composable
fun ExamVocabularyBottomSheetDialog(
    uiParagraph: List<UiParagraph>,
    readerSetting: ReaderSetting?,
    onClickedDismiss: () -> Unit,
    onNextParagraph: () -> Unit
) {
    val viewModel = getViewModel<ExamVocabularyViewModel>()
    viewModel.setUiParagraphs(uiParagraph)
    viewModel.setUpExam()
    OnEvent(event = viewModel.nextParagraph, onEvent = {
        onNextParagraph()
    })
    BottomSheetDialog(
        onDismissRequest = {
            onClickedDismiss()
        },
        properties = BottomSheetDialogProperties(
            dismissWithAnimation = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            behaviorProperties = BottomSheetBehaviorProperties(
                halfExpandedRatio = 0.8f,
                state = BottomSheetBehaviorProperties.State.HalfExpanded
            )
        ),
    ) {
        val configuration = LocalConfiguration.current

        val height = configuration.screenHeightDp

        Box(
            modifier = Modifier
                .height(height.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(ReaderSettingUtil.getBackgroundModalColor(readerSetting?.backgroundMode))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${stringResource(id = R.string.vocabulary_paragraph_at_label)} ${viewModel.currentParagraph.observeAsState().value}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = ReaderSettingUtil.getFontColor(value = readerSetting?.backgroundMode)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        Modifier
                            .size(18.dp)
                            .clickable {
                                onClickedDismiss()
                            },
                        colorFilter = ColorFilter.tint(ReaderSettingUtil.getDrawableTint(value = readerSetting?.backgroundMode))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorResource(id = R.color.gray300))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "คำที่ ${viewModel.currentIndexTakingExam.observeAsState().value?.plus(1)}/10",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ReaderSettingUtil.getFontColor(value = readerSetting?.backgroundMode)
                )
                Row {
                    Text(
                        text = "(ตอบผิด ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ReaderSettingUtil.getFontColor(value = readerSetting?.backgroundMode)
                    )
                    val totalWrong = viewModel.totalWrong.observeAsState().value
                    Text(
                        text = "$totalWrong/3)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (totalWrong == 0) ReaderSettingUtil.getFontColor(value = readerSetting?.backgroundMode) else colorResource(
                            id = R.color.red_srt
                        )
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    if (viewModel.isLoading.observeAsState().value == true) {
                        Box(
                            modifier = Modifier.padding(top = 22.dp),
                        ) {
                            CircularProgressIndicator(color = colorResource(id = R.color.purple_500))
                        }
                    } else {
                        ContentVocab(viewModel, readerSetting)
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentVocab(viewModel: ExamVocabularyViewModel, readerSetting: ReaderSetting?) {
    if (viewModel.isShowFireWork.observeAsState().value == true) {
        //Loader()
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                viewModel.onTextSpeech(context)
            }) {
            Image(
                painter = painterResource(id = R.drawable.ic_volume),
                modifier = Modifier.size(28.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    ReaderSettingUtil.getDrawableTint(
                        value = readerSetting?.backgroundMode
                    )
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${viewModel.currentVocab.observeAsState().value}",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_500)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            OutlinedButton(
                border = BorderStroke(
                    1.dp,
                    colorResource(id = R.color.gray500)
                ),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(
                    colorResource(
                        id = getColorButtonA(
                            viewModel,
                            readerSetting
                        )
                    )
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.checkAnswer(1)
                },
            ) {
                Text(
                    text = "A. ${viewModel.answerA.observeAsState().value}",
                    color = colorResource(id = getColorButtonTextA(viewModel, readerSetting))
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedButton(
                border = BorderStroke(
                    1.dp,
                    colorResource(id = R.color.gray500)
                ),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(
                    colorResource(
                        id = getColorButtonB(
                            viewModel,
                            readerSetting
                        )
                    )
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.checkAnswer(2)
                },
            ) {
                Text(
                    text = "B. ${viewModel.answerB.observeAsState().value}",
                    color = colorResource(id = getColorButtonTextB(viewModel, readerSetting))
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (viewModel.isShowTextTestAgain.observeAsState().value == true) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.wrong_more_than_three_label),
                        color = colorResource(id = R.color.red_srt),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.re_start_label),
                        color = colorResource(id = R.color.red_srt),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            viewModel.setUpExam()
                        },
                    )
                }
            }
            if (viewModel.isShowTextNextParagraph.observeAsState().value == true) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.good_job_read_next_paragraph_label),
                        color = colorResource(id = R.color.green_sukhumvit),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.read_next_paragraph_label),
                        color = colorResource(id = R.color.green_sukhumvit),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            viewModel.onNextParagraph()
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun getColorButtonA(viewModel: ExamVocabularyViewModel, readerSetting: ReaderSetting?): Int {
    return when (viewModel.correctState.observeAsState().value) {
        UiCorrectState.CorrectA -> {
            R.color.green_sukhumvit
        }
        UiCorrectState.InCorrectA -> {
            R.color.red_srt
        }
        else -> {
            ReaderSettingUtil.getBackgroundColorInt(value = readerSetting?.backgroundMode)
        }
    }
}

@Composable
fun getColorButtonTextA(viewModel: ExamVocabularyViewModel, readerSetting: ReaderSetting?): Int {
    return when (viewModel.correctState.observeAsState().value) {
        UiCorrectState.CorrectA -> {
            R.color.only_white
        }
        UiCorrectState.InCorrectA -> {
            R.color.only_white
        }
        else -> {
            ReaderSettingUtil.getFontColorInt(value = readerSetting?.backgroundMode)
        }
    }
}

@Composable
fun getColorButtonB(viewModel: ExamVocabularyViewModel, readerSetting: ReaderSetting?): Int {
    return when (viewModel.correctState.observeAsState().value) {
        UiCorrectState.CorrectB -> {
            R.color.green_sukhumvit
        }
        UiCorrectState.InCorrectB -> {
            R.color.red_srt
        }
        else -> {
            ReaderSettingUtil.getBackgroundColorInt(value = readerSetting?.backgroundMode)
        }
    }
}

@Composable
fun getColorButtonTextB(viewModel: ExamVocabularyViewModel, readerSetting: ReaderSetting?): Int {
    return when (viewModel.correctState.observeAsState().value) {
        UiCorrectState.CorrectB -> {
            R.color.only_white
        }
        UiCorrectState.InCorrectB -> {
            R.color.only_white
        }
        else -> {
            ReaderSettingUtil.getFontColorInt(value = readerSetting?.backgroundMode)
        }
    }
}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire_work))
    val progress by animateLottieCompositionAsState(composition)


    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.OPACITY,
            value = colorResource(id = R.color.black),
            keyPath = arrayOf(
                "H2",
                "Shape 1",
                "Fill 1",
            )
        ),
    )

    LottieAnimation(
        applyOpacityToLayers = true,
        composition = composition,
        dynamicProperties = dynamicProperties,
        progress = { progress },
    )
}
