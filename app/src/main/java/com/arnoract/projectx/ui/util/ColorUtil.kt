package com.arnoract.projectx.ui.util

import androidx.compose.ui.graphics.Color

object ColorUtil {
    fun hexToColor(hex: String): Color {
        val strippedHex = hex.removePrefix("#")
        val red = strippedHex.substring(0, 2).toInt(16) / 255f
        val green = strippedHex.substring(2, 4).toInt(16) / 255f
        val blue = strippedHex.substring(4, 6).toInt(16) / 255f
        return Color(red, green, blue)
    }
}