package com.arnoract.projectx.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContentTitleSection(
    modifier: Modifier = Modifier,
    icon: Painter,
    value: String,
    isShowSeeAll: Boolean = false,
    onClickedSeeMore: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = icon,
            modifier = Modifier.size(21.dp),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            modifier = modifier.weight(1f),
            maxLines = 2,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
        if (isShowSeeAll) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ดูทั้งหมด",
                modifier = Modifier.clickable {
                    onClickedSeeMore()
                },
                maxLines = 2,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}