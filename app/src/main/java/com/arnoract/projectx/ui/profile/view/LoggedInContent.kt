package com.arnoract.projectx.ui.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.profile.model.UiUser

@Composable
fun LoggedInContent(
    data: UiUser,
    isSubscribed: State<Boolean?>,
    onClickedSignOut: () -> Unit,
    onClickedGetGoldCoin: () -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            val painter = rememberAsyncImagePainter(model = data.profileUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorResource(id = R.color.red_orange)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.height(75.dp), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .wrapContentHeight(),
                ) {
                    Text(
                        text = data.displayName,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = data.email,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.gray700),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(colorResource(id = R.color.white))
                .height(1.dp)
                .fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.crown),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            val textStatus =
                if (isSubscribed.value == true) stringResource(id = R.string.member_premium_label) else stringResource(
                    id = R.string.member_normal_label
                )
            Text(
                text = textStatus,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = if (isSubscribed.value == true) R.color.gold_bts else R.color.black)
            )
            if (isSubscribed.value == false) {
                Box(modifier = Modifier
                    .height(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        onClickedGetGoldCoin()
                    }
                    .background(colorResource(id = R.color.gold)),
                    contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_ads),
                            modifier = Modifier.size(17.dp),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.black))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.get_coin_free_label),
                            modifier = Modifier,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.black),
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .background(colorResource(id = R.color.white))
                .height(1.dp)
                .fillMaxWidth()
        )
        InformationContent()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_sign_out),
                modifier = Modifier.size(17.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.red_srt))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.sign_out_label),
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clickable {
                        onClickedSignOut()
                    },
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.red_srt)
            )
        }
    }
}