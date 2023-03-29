package com.arnoract.projectx.ui.lesson.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.lesson.model.UiLessonSentence

@Composable
fun LessonSentenceItem(model: UiLessonSentence, onClickedItem: () -> Unit) {
    Box(modifier = Modifier
        .padding(bottom = 16.dp)
        .clickable {
            onClickedItem()
        }) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(model.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .alpha(if (model.isComingSoon) 0.5f else 1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.gray300))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = model.titleTh,
                    modifier = Modifier.alpha(if (model.isComingSoon) 0.5f else 1f),
                    maxLines = 1,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = model.descriptionTh,
                    modifier = Modifier.alpha(if (model.isComingSoon) 0.5f else 1f),
                    maxLines = 2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.gray500)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_coin),
                        modifier = Modifier
                            .size(20.dp),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = model.priceCoin,
                        modifier = Modifier.alpha(if (model.isComingSoon) 0.5f else 1f),
                        maxLines = 1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.black)
                    )
                }
            }
        }
    }
}