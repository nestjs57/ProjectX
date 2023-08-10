package com.arnoract.projectx.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

@Composable
fun ProgressView(percent: Int, textProgress: String) {
    val mPercent = if (percent >= 100) {
        100f
    } else if (percent < 0.0) {
        0f
    } else {
        percent.div(100f)
    }
    val mColorProgress = if (mPercent == 100f) R.color.green_sukhumvit else R.color.red_orange
    val mTextColorProgress = if (mPercent == 100f) R.color.white else R.color.black
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(
                    color = colorResource(R.color.gray300), shape = RoundedCornerShape(56.dp)
                )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(mPercent)
                .height(12.dp)
                .background(
                    color = colorResource(mColorProgress), shape = RoundedCornerShape(56.dp)
                )
        )
        Text(
            text = textProgress,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = colorResource(id = mTextColorProgress),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
    }
}

@Preview
@Composable
private fun WhiteBackgroundWithRoundedCornersPreview() {
    ProgressView(percent = 100, textProgress = "50%")
}