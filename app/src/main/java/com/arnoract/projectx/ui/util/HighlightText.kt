package com.arnoract.projectx.ui.util

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.arnoract.projectx.R

@Composable
fun HighlightText(text: String, keyword: String) {
    val srtKeyword = keyword.lowercase().replace(",", "").replace(".", "")
    if (!text.lowercase().contains(srtKeyword)) {
        Text(text)
        return
    }
    val startIndex = text.lowercase().indexOf(srtKeyword)
    val annotatedText = AnnotatedString.Builder(text.substring(0, startIndex))
    if (startIndex >= 0) {
        annotatedText.withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.highlight_text),
                fontWeight = FontWeight.Bold
            )
        ) {
            append(text.substring(startIndex, startIndex + srtKeyword.length))
        }
    }
    annotatedText.append(text.substring(startIndex + srtKeyword.length))
    Text(text = annotatedText.toAnnotatedString())
}