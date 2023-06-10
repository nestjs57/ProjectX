package com.arnoract.projectx.ui.reader

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.arnoract.projectx.R
import com.arnoract.projectx.ui.reader.model.SettingBackground

object ReaderSettingUtil {

    @Composable
    fun getFontColor(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.only_black)
            SettingBackground.NIGHT -> colorResource(id = R.color.only_white)
            else -> colorResource(id = R.color.black)
        }
    }

    @Composable
    fun getFontColorInt(value: SettingBackground?): Int {
        return when (value) {
            SettingBackground.DAY -> R.color.only_black
            SettingBackground.NIGHT -> R.color.only_white
            else -> R.color.black
        }
    }

    @Composable
    fun getAppBarColor(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.only_white)
            SettingBackground.NIGHT -> colorResource(id = R.color.app_bar_dark_mode)
            else -> colorResource(id = R.color.only_white)
        }
    }

    @Composable
    fun getBackgroundColor(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.only_white)
            SettingBackground.NIGHT -> colorResource(id = R.color.bg_bar_dark_mode)
            else -> colorResource(id = R.color.only_white)
        }
    }

    @Composable
    fun getBackgroundColorInt(value: SettingBackground?): Int {
        return when (value) {
            SettingBackground.DAY -> R.color.only_white
            SettingBackground.NIGHT -> R.color.bg_bar_dark_mode
            else -> R.color.only_white
        }
    }

    @Composable
    fun getFontColorPurple(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.purple_500)
            SettingBackground.NIGHT -> colorResource(id = R.color.purple_700)
            else -> colorResource(id = R.color.purple_500)
        }
    }

    @Composable
    fun getBackgroundModalColor(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.only_white)
            SettingBackground.NIGHT -> colorResource(id = R.color.bg_bar_dark_mode)
            else -> colorResource(id = R.color.only_white)
        }
    }

    fun getFontColorString(value: SettingBackground?): String {
        return when (value) {
            SettingBackground.DAY -> "#282828"
            SettingBackground.NIGHT -> "#FFFFFF"
            else -> "#282828"
        }
    }

    fun getBackgroundModalColorString(value: SettingBackground?): String {
        return when (value) {
            SettingBackground.DAY -> "#FFFFFF"
            SettingBackground.NIGHT -> "#282828"
            else -> "#FFFFFF"
        }
    }

    @Composable
    fun getDrawableTint(value: SettingBackground?): Color {
        return when (value) {
            SettingBackground.DAY -> colorResource(id = R.color.only_black)
            SettingBackground.NIGHT -> colorResource(id = R.color.only_white)
            else -> colorResource(id = R.color.only_black)
        }
    }
}