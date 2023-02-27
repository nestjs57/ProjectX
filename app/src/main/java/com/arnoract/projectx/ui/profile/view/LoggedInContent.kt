package com.arnoract.projectx.ui.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.profile.model.UiUser

@Composable
fun LoggedInContent(data: UiUser, onClickedSignOut: () -> Unit) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row() {
            val painter =
                rememberImagePainter(data = data.profileUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.red_orange)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .height(75.dp),
                contentAlignment = Alignment.Center
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
        InformationContent()
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