package com.arnoract.projectx.ui.reader.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getAppBarColor
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getBackgroundColor
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getDrawableTint
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getFontColor
import com.arnoract.projectx.ui.reader.ReaderSettingUtil.getFontColorPurple
import com.arnoract.projectx.ui.reader.model.ReaderSetting
import com.arnoract.projectx.ui.reader.model.SettingBackground
import com.arnoract.projectx.ui.reader.model.SettingFontSize
import com.arnoract.projectx.ui.reader.model.UiParagraph
import com.google.accompanist.flowlayout.FlowRow

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ReaderContent(
    titleTh: String,
    titleEn: String,
    uiParagraph: List<List<UiParagraph>>,
    uiTranSlateParagraph: List<String>,
    uiContentHTML: List<String>,
    currentParagraphSelected: Int,
    readerSetting: ReaderSetting?,
    onClickedSelectVocabulary: (UiParagraph) -> Unit,
    onClickedNextParagraph: () -> Unit,
    onClickedPreviousParagraph: () -> Unit,
    onTextSpeech: (String) -> Unit,
    onClickedBack: () -> Unit,
    onClickedTextSize: (SettingFontSize) -> Unit,
    onClickedBackgroundModel: (SettingBackground) -> Unit,
    onClickedStructureSentence: (String) -> Unit
) {

    var paragraphNumber: Int by remember {
        mutableStateOf(currentParagraphSelected)
    }
    paragraphNumber = currentParagraphSelected

    var isShowDialogSetting by remember {
        mutableStateOf(false)
    }
    var isShowDialogTranslateParagraph by remember {
        mutableStateOf(false)
    }

    if (isShowDialogSetting) {
        BottomDialogSettingReader(
            readerSetting,
            onClickedDismiss = {
                isShowDialogSetting = false
            },
            onClickedTextSize = onClickedTextSize,
            onClickedBackgroundModel = onClickedBackgroundModel
        )
    }

    if (isShowDialogTranslateParagraph) {
        BottomDialogTranslateParagraph(
            titleTh, readerSetting, uiTranSlateParagraph[currentParagraphSelected]
        ) {
            isShowDialogTranslateParagraph = false
        }
    }

    Column(
        modifier = Modifier.background(getBackgroundColor(value = readerSetting?.backgroundMode))
    ) {
        val underLineColor =
            if (readerSetting?.backgroundMode == SettingBackground.DAY) colorResource(id = R.color.gray300) else colorResource(
                id = R.color.transparent
            )
        ToolBar(paragraphNumber,
            uiParagraph.size,
            readerSetting,
            onClickedBack,
            onClickedSetting = {
                isShowDialogSetting = true
            })
        Spacer(
            modifier = Modifier
                .background(underLineColor)
                .height(2.dp)
                .fillMaxWidth()
        )

        var vocabulary: String by remember {
            mutableStateOf("")
        }
        var translate: String by remember {
            mutableStateOf("")
        }
        var example: String by remember {
            mutableStateOf("")
        }
        var exampleTranslate: String by remember {
            mutableStateOf("")
        }

        var isShowTranslate by remember {
            mutableStateOf(false)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        ) {
            item {
                TitleSection(titleTh, titleEn, readerSetting)
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            item {
                Column {
                    ParagraphSection(uiParagraph[paragraphNumber],
                        setting = readerSetting,
                        onClickedVocabulary = {
                            onClickedSelectVocabulary(it)
                        },
                        onTranslate = {
                            vocabulary = if (it?.vocabulary == null) "" else "${it.vocabulary} : "
                            translate = it?.translate ?: ""
                            example = it?.example ?: ""
                            exampleTranslate = it?.example_translate ?: ""
                        })

                    val textSize = when (readerSetting?.fontSizeMode) {
                        SettingFontSize.SMALL -> 12.sp
                        SettingFontSize.NORMAL -> 14.sp
                        SettingFontSize.LARGE -> 16.sp
                        else -> 14.sp
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_text),
                            modifier = Modifier.size(21.dp),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.show_translate_this_paragraph_label),
                            color = getFontColor(value = readerSetting?.backgroundMode),
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                isShowDialogTranslateParagraph = true
                            },
                            fontSize = textSize
                        )
                    }

                    if (uiContentHTML.isNotEmpty()) {
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_auto_stories),
                                modifier = Modifier.size(21.dp),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.structure_sentence_label),
                                color = getFontColor(value = readerSetting?.backgroundMode),
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    onClickedStructureSentence(uiContentHTML[paragraphNumber])
                                },
                                fontSize = textSize
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.gray500))
                    )

                    if (vocabulary.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                onTextSpeech(vocabulary)
                            }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_volume),
                                modifier = Modifier.size(22.dp),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(getDrawableTint(value = readerSetting?.backgroundMode))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = vocabulary,
                                color = getFontColorPurple(readerSetting?.backgroundMode),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Text(
                        text = translate,
                        color = getFontColor(readerSetting?.backgroundMode),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        val isFirstParagraph = paragraphNumber < 1
        val isLastParagraph = paragraphNumber == uiParagraph.size.minus(1)
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(56.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .height(48.dp)
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    if (isFirstParagraph) return@clickable
                    isShowTranslate = false
                    onClickedPreviousParagraph()
                }
                .background(colorResource(id = if (isFirstParagraph) R.color.gray500 else R.color.purple_500))
                .weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.previous_label),
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(modifier = Modifier
                .height(48.dp)
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    if (isLastParagraph) return@clickable
                    isShowTranslate = false
                    onClickedNextParagraph()
                }
                .background(colorResource(id = if (isLastParagraph) R.color.gray500 else R.color.purple_500))
                .weight(1f), contentAlignment = Alignment.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.next_label),
                        modifier = Modifier,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white),
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolBar(
    paragraphNumber: Int,
    uiParagraphSize: Int,
    setting: ReaderSetting?,
    onClickedBack: () -> Unit,
    onClickedSetting: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .background(getAppBarColor(setting?.backgroundMode)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickedBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Menu Btn",
                tint = getDrawableTint(setting?.backgroundMode)
            )
        }
        Text(
            text = "ย่อหน้า ${paragraphNumber.plus(1)}/${uiParagraphSize}",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = getFontColor(setting?.backgroundMode)
        )

        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_sliders_unselect),
                contentDescription = null,
                Modifier
                    .size(18.dp)
                    .clickable {
                        onClickedSetting()
                    },
                colorFilter = ColorFilter.tint(getDrawableTint(value = setting?.backgroundMode))
            )
        }
    }
}

@Composable
private fun TitleSection(titleTh: String, titleEn: String, readerSetting: ReaderSetting?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = titleEn,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(end = 8.dp),

            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = when (readerSetting?.fontSizeMode) {
                SettingFontSize.SMALL -> 12.sp
                SettingFontSize.NORMAL -> 14.sp
                SettingFontSize.LARGE -> 16.sp
                else -> 14.sp
            },
            color = getFontColor(readerSetting?.backgroundMode)
        )
    }
}

@Composable
private fun ParagraphSection(
    paragraph: List<UiParagraph>,
    setting: ReaderSetting?,
    onClickedVocabulary: (UiParagraph) -> Unit,
    onTranslate: (UiParagraph?) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(top = 8.dp), crossAxisSpacing = 8.dp, mainAxisSpacing = 4.dp
    ) {
        if (!paragraph.any { it.isSelected }) {
            onTranslate(null)
        }
        paragraph.forEachIndexed { _, uiParagraph ->
            if (uiParagraph.isSelected) {
                onTranslate(uiParagraph)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (uiParagraph.isSelected) colorResource(id = R.color.purple_500) else getBackgroundColor(
                            setting?.backgroundMode
                        )
                    )
                    .padding(horizontal = 4.dp)
            ) {
                val textDecoration = if (uiParagraph.translate.isNotBlank()) {
                    if (uiParagraph.isSelected) {
                        TextDecoration.None
                    } else {
                        TextDecoration.Underline
                    }
                } else {
                    TextDecoration.None
                }
                Text(
                    text = uiParagraph.vocabulary,
                    modifier = Modifier.clickable(indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = {
                            if (uiParagraph.translate.isNotBlank()) {
                                onClickedVocabulary(uiParagraph)
                            }
                        }),
                    textDecoration = textDecoration,
                    fontSize = when (setting?.fontSizeMode) {
                        SettingFontSize.SMALL -> 12.sp
                        SettingFontSize.NORMAL -> 14.sp
                        SettingFontSize.LARGE -> 16.sp
                        else -> 14.sp
                    },
                    color = if (uiParagraph.isSelected) colorResource(id = R.color.white) else getFontColor(
                        setting?.backgroundMode
                    )
                )
            }
        }
    }
}