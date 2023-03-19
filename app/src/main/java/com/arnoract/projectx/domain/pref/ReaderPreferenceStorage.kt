package com.arnoract.projectx.domain.pref

import android.content.SharedPreferences
import androidx.core.content.edit

interface ReaderPreferenceStorage {
    var settingFontSize: Int
    var settingBackgroundMode: Int
}

class SharedPreferencesReaderPreferenceStorage(private val sharedPreferences: SharedPreferences) :
    ReaderPreferenceStorage {

    override var settingFontSize: Int
        get() = sharedPreferences.getInt(KEY_FONT_SETTING_READER, 2)
        set(value) {
            sharedPreferences.edit(commit = true) {
                putInt(KEY_FONT_SETTING_READER, value)
            }
        }

    override var settingBackgroundMode: Int
        get() = sharedPreferences.getInt(KEY_BACKGROUND_SETTING_READER, 1)
        set(value) {
            sharedPreferences.edit(commit = true) {
                putInt(KEY_BACKGROUND_SETTING_READER, value)
            }
        }

    companion object {
        private const val KEY_FONT_SETTING_READER = "font-setting-reader"
        private const val KEY_BACKGROUND_SETTING_READER = "background-setting-reader"
    }
}