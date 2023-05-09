package com.arnoract.projectx.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

@Composable
fun ExampleFeatureDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageDrawable: Int,
    openDialogCustom: MutableState<Boolean>
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)
            .background(colorResource(id = R.color.transparent)),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(colorResource(id = R.color.transparent))
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
                    color = colorResource(id = R.color.black),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = imageDrawable),
                    modifier = Modifier.size(350.dp),
                    contentDescription = null,
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        stringResource(id = R.string.close_label),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.purple_500),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}