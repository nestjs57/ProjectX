package com.arnoract.projectx.ui.home.view

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
import com.arnoract.projectx.ui.home.model.UiArticleHorizontalItem
import com.arnoract.projectx.ui.home.model.mapper.UiArticleCategoryToCategoryLabelMapper
import com.arnoract.projectx.util.FormatUtils.formatNumberWithOrWithOutDecimal

@Composable
fun ArticleHorizontalItem(model: UiArticleHorizontalItem, onClickedItem: (String) -> Unit) {
    Row(modifier = Modifier
        .padding(bottom = 16.dp)
        .clickable {
            onClickedItem(model.id)
        }) {
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
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = model.titleTh,
                modifier = Modifier,
                maxLines = 2,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "หมวดหมู่ : ${
                    stringResource(
                        id = UiArticleCategoryToCategoryLabelMapper.map(
                            model.category
                        )
                    )
                }",
                maxLines = 2,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = model.descriptionTh,
                Modifier.alpha(0.5f),
                maxLines = 2,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Row {
                Box(
                    modifier = Modifier, contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_eye),
                        modifier = Modifier
                            .size(21.dp)
                            .align(Alignment.CenterStart),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.gray500))
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formatNumberWithOrWithOutDecimal(model.viewCount.toDouble()),
                    Modifier.alpha(0.5f),
                    maxLines = 3,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleHorizontalItemPreview() {
    ArticleHorizontalItem(UiArticleHorizontalItem(
        id = "id",
        imageUrl = "imageUrl",
        titleTh = "ทำไมเล่นเกมไม่สนุกเหมือนตอนเด็ก",
        titleEn = "titleEn",
        descriptionTh = "ตำนานของการกักขังชั่วขณะของเมือง ซึ่งการหลบหนีเป็นไปไม่ได้และการเบี่ยงเบนจากสภาพที่เป็นอยู่ก็พบกับการกล่าวซ้ำอย่างไม...",
        descriptionEn = "",
        viewCount = 30,
        category = UiArticleCategory.POSITIVE_THINKING
    ), {})
}