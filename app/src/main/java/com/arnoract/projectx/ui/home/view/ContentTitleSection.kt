package com.arnoract.projectx.ui.home.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun ContentTitleSection(
    modifier: Modifier = Modifier,
    value: String
) {
    Text(
        text = value,
        modifier = modifier,
        maxLines = 2,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
    )
}