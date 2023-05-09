package com.arnoract.projectx.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

@Composable
fun PayWallSubscriptionDialog(
    openDialogCustom: MutableState<Boolean>,
    onClickedSubscription: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            Modifier
                .background(Color.White)
                .background(colorResource(id = R.color.bg_base_dialog)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.crown),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.gold))
                )

                Text(
                    text = stringResource(id = R.string.member_privileges),
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
                    text = stringResource(id = R.string.member_privileges_description),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.gray700),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                )
            }
            LayoutCheckFeature(stringResource(id = R.string.access_all_article_label))
            Spacer(modifier = Modifier.height(4.dp))
            LayoutCheckFeature(stringResource(id = R.string.auto_save_article_label))
            Spacer(modifier = Modifier.height(4.dp))
            LayoutCheckFeature(stringResource(id = R.string.no_ads_privileges_label))
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.member_privileges_price_can_cancel_every_time),
                    modifier = Modifier,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(id = R.color.gray700),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        openDialogCustom.value = false
                        onClickedSubscription()
                    }
                    .background(colorResource(id = R.color.gold)),
                    contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.get_coin_free_label),
                            modifier = Modifier,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = colorResource(id = R.color.black),
                        )
                    }
                }
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        stringResource(id = R.string.close_label),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LayoutCheckFeature(text: String) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(6.dp)
                .width(25.dp)
                .height(25.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(colorResource(id = R.color.green_sukhumvit)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                modifier = Modifier
                    .size(15.dp)
                    .align(Alignment.Center),
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.white))
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
        )
    }
}