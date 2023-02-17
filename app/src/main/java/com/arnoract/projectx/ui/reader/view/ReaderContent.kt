package com.arnoract.projectx.ui.reader.view

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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.reader.model.UiParagraph
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ReaderContent(
    titleTh: String,
    titleEn: String,
    uiParagraph: List<List<UiParagraph>>,
    currentParagraphSelected: Int,
    onClickedSelectVocabulary: (UiParagraph) -> Unit,
    onClickedNextParagraph: () -> Unit,
    onClickedPreviousParagraph: () -> Unit,
    onClickedBack: () -> Unit
) {

    val paragraphNumber: Int by remember {
        mutableStateOf(currentParagraphSelected)
    }

    Column {
        ToolBar(paragraphNumber, uiParagraph.size, onClickedBack)
        Spacer(
            modifier = Modifier
                .background(colorResource(id = R.color.gray300))
                .height(2.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            item {
                TitleSection(titleTh, titleEn)
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            item {
                var vocabulary: String by remember {
                    mutableStateOf("")
                }
                var translate: String by remember {
                    mutableStateOf("")
                }
                var note: String by remember {
                    mutableStateOf("")
                }
                Column {
                    ParagraphSection(uiParagraph[paragraphNumber],
                        onClickedVocabulary = {
                            onClickedSelectVocabulary(it)
                        },
                        onTranslate = {
                            vocabulary = if (it?.vocabulary == null) "" else "${it.vocabulary} : "
                            translate = it?.translate ?: ""
                            note = it?.note ?: ""
                        })
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.gray500))
                    )
                    Text(
                        text = vocabulary,
                        color = colorResource(id = R.color.purple_500),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = translate,
                        color = colorResource(id = R.color.black),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = note)
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {

                    }
                    .background(colorResource(id = R.color.purple_500))
                    .weight(1f), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ก่อนหน้า",
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {

                    }
                    .background(colorResource(id = R.color.purple_500))
                    .weight(1f), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ถัดไป",
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
            }
        }
    }
}

@Composable
private fun ToolBar(paragraphNumber: Int, uiParagraphSize: Int, onClickedBack: () -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickedBack) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Menu Btn")
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
        )
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Menu Btn")
        }
    }
}

@Composable
private fun TitleSection(titleTh: String, titleEn: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = titleEn,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(end = 8.dp),

            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ParagraphSection(
    paragraph: List<UiParagraph>,
    onClickedVocabulary: (UiParagraph) -> Unit,
    onTranslate: (UiParagraph?) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(top = 8.dp),
        crossAxisSpacing = 8.dp,
        mainAxisSpacing = 4.dp
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
                        if (uiParagraph.isSelected) colorResource(id = R.color.purple_500) else colorResource(
                            id = R.color.white
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
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                            onClick = {
                                if (uiParagraph.translate.isNotBlank()) {
                                    onClickedVocabulary(uiParagraph)
                                }
                            }
                        ),
                    textDecoration = textDecoration,
                    fontSize = 14.sp,
                    color = if (uiParagraph.isSelected) colorResource(id = R.color.white) else colorResource(
                        id = R.color.black
                    )
                )
            }
        }
    }
}