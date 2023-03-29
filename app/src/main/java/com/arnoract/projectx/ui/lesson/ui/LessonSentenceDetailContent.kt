package com.arnoract.projectx.ui.lesson.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.lesson.model.UiLessonSentence

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonSentenceDetailContent(data: UiLessonSentence, onClickedTranState: (String) -> Unit) {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.titleTh,
                    color = colorResource(
                        id = R.color.black
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        item {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.descriptionTh,
                    color = colorResource(
                        id = R.color.black
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }

        stickyHeader {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.white)
                    )
                    .padding(start = 24.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_text),
                    modifier = Modifier.size(21.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.example_sentence_with_colon_label),
                    color = colorResource(
                        id = R.color.black
                    ),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        items(data.sentences?.size ?: 0) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${it.plus(1)}) ${data.sentences?.get(it)?.sentence ?: ""}",
                            color = colorResource(
                                id = R.color.black
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                onClickedTranState(data.sentences?.get(it)?.sentence ?: "")
                            }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = data.sentences?.get(it)?.translate ?: "",
                            color = colorResource(
                                id = R.color.gray500
                            ),
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_volume),
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                onClickedTranState(data.sentences?.get(it)?.sentence ?: "")
                            },
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.purple_500))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.gray300)
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}