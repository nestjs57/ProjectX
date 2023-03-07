package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
fun ArticleVerticalItem(model: UiArticleVerticalItem, onClickedItem: () -> Unit) {
    Column(modifier = Modifier
        .width(115.dp)
        .clip(RoundedCornerShape(6.dp))
        .clickable {
            onClickedItem()
        }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(model.imageUrl)
                .crossfade(true).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(115.dp)
                .height(175.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.gray300))
        )
        Text(
            text = model.titleTh,
            modifier = Modifier
                .padding(top = 2.dp),
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