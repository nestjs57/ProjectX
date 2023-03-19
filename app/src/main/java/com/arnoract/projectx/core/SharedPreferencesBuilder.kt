package com.arnoract.projectx.core

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesBuilder(val context: Context) {
    fun buildDefault(): SharedPreferences {
        return context.applicationContext.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    fun buildUserPreference(): SharedPreferences {
        return context.applicationContext.getSharedPreferences("preference", Context.MODE_PRIVATE)
    }

    fun buildFeatureFlagPreference(): SharedPreferences {
        return context.applicationContext.getSharedPreferences("feature-flag", Context.MODE_PRIVATE)
    }

    fun buildUiStatePreference(): SharedPreferences {
        return context.applicationContext.getSharedPreferences("ui-state", Context.MODE_PRIVATE)
    }
}
