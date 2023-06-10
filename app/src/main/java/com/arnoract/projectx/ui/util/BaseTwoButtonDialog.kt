package com.arnoract.projectx.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

@Composable
fun BaseTwoButtonDialog(
    title: String,
    description: String,
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            Modifier.background(colorResource(id = R.color.bg_base_dialog))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
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

                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                )
            }
            Row(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    onClickLeftButton()
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
                        onClickRightButton()
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