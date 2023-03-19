package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.home.model.UiArticleCategory
import com.arnoract.projectx.ui.home.model.UiArticleVerticalItem
import com.arnoract.projectx.ui.home.model.mapper.UiArticleCategoryToCategoryLabelMapper

@Composable
fun ArticleVerticalItem(
    model: UiArticleVerticalItem,
    modifier: Modifier = Modifier,
    onClickedItem: () -> Unit
) {
    Box(modifier = Modifier) {
        Column(modifier = modifier
            .width(95.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                if (!model.isBlock) {
                    onClickedItem()
                }
            }) {
            Box(modifier = Modifier.alpha(if (model.isBlock) 0.6f else 1f)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(model.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(95.dp)
                        .height(155.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = R.color.gray300))
                )
                val isComplete = model.progress == "100"
                if (model.progress != null) {
                    Card(
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.BottomEnd)
                            .width(25.dp)
                            .height(25.dp),
                        elevation = 10.dp,
                        backgroundColor = if (isComplete) colorResource(id = R.color.green_sukhumvit) else MaterialTheme.colors.surface,
                    ) {
                        Box {
                            if (isComplete) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_check),
                                    modifier = Modifier
                                        .size(17.dp)
                                        .align(Alignment.Center),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white))
                                )
                            } else {
                                Text(
                                    text = "${model.progress}%",
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    maxLines = 2,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }
            Text(
                text = model.titleTh,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .alpha(if (model.isBlock) 0.6f else 1f),
                maxLines = 2,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(id = UiArticleCategoryToCategoryLabelMapper.map(model.category)),
                Modifier.alpha(0.5f),
                maxLines = 1,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookVerticalItemPreView() {
    ArticleVerticalItem(
        UiArticleVerticalItem(
            id = "id",
            imageUrl = "imageUrl",
            titleTh = "ตั้งเป้าหมายไว้ ดิบดี แต่พอทำจริงทำไมยากจัง",
            titleEn = "",
            category = UiArticleCategory.POSITIVE_THINKING
        )
    ) {

    }
}