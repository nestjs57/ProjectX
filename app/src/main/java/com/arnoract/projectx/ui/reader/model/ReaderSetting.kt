package com.arnoract.projectx.ui.reader.model

data class ReaderSetting(
    val fontSizeMode: SettingFontSize = SettingFontSize.NORMAL,
    val backgroundMode: SettingBackground = SettingBackground.DAY
)

enum class SettingFontSize {
    SMALL,
    NORMAL,
    LARGE
}

enum class SettingBackground {
    DAY,
    NIGHT
}
