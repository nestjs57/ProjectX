package com.arnoract.projectx.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.lesson.ui.ConfirmPurchaseState

@Composable
fun ConfirmPurchaseLessonDialog(
    openDialogState: MutableState<ConfirmPurchaseState>, onClickedConfirm: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            Modifier.background(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.access_to_content_title_label),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                when (val state = openDialogState.value) {
                    is ConfirmPurchaseState.Show -> {
                        val annotatedString = buildAnnotatedString {
                            append("${stringResource(id = R.string.you_must_use_label)} ")
                            append("${state.data.coin} ")
                            appendInlineContent(id = "imageId")
                            append(" ${stringResource(id = R.string.for_access_to_content_press_confirm_to_use_coin_label)}")
                        }
                        val inlineContentMap = mapOf("imageId" to InlineTextContent(
                            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_coin),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = ""
                            )
                        })

                        Text(
                            text = state.data.titleTh,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            fontSize = 16.sp,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = annotatedString,
                            inlineContent = inlineContentMap,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                        )
                    }
                    else -> {}
                }
            }
            Row(
                Modifier
                    .align(CenterHorizontally)
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    openDialogState.value = ConfirmPurchaseState.Hide
                }) {
                    Text(
                        stringResource(id = R.string.close_label),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.purple_500),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        onClickedConfirm()
                    }
                    .background(colorResource(R.color.purple_500))
                    .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.confirm_label),
                        modifier = Modifier,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white),
                    )
                }
            }
        }
    }
}





