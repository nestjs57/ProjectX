package com.arnoract.projectx.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arnoract.projectx.R

// Set of Material typography styles to start with
//private val fontFamily = FontFamily(
//    Font(R.font.db_heavent_now_regular, FontWeight.Light),
//    Font(R.font.db_heavent_now_regular, FontWeight.Normal),
//    Font(R.font.db_heavent_now_italic, FontWeight.Normal, FontStyle.Italic),
//    Font(R.font.db_heavent_now_bold, FontWeight.Medium),
//    Font(R.font.db_heavent_now_bold, FontWeight.Bold)
//)

private val fontFamily = FontFamily(
    Font(R.font.mn_paethai_bystorylog_regular, FontWeight.Light),
    Font(R.font.mn_paethai_bystorylog_regular, FontWeight.Normal),
    Font(R.font.mn_paethai_bystorylog_ltalic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.mn_paethai_bystorylog_demibold, FontWeight.Medium),
    Font(R.font.mn_paethai_bystorylog_demibold, FontWeight.Bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)