package com.arnoract.projectx.domain.pref

import android.content.SharedPreferences
import androidx.core.content.edit

interface UserPreferenceStorage {
    var userId: String?
    var displayName: String?
    var profileUrl: String?
    var email: String?
    var coin: Int?
}

class SharedPreferencesUserPreferenceStorage(private val sharedPreferences: SharedPreferences) :
    UserPreferenceStorage {

    override var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, "") ?: ""
        set(value) {
            sharedPreferences.edit(commit = true) {
                putString(KEY_USER_ID, value)
            }
        }

    override var displayName: String?
        get() = sharedPreferences.getString(KEY_DISPLAY_NAME, "") ?: ""
        set(value) {
            sharedPreferences.edit(commit = true) {
                putString(KEY_DISPLAY_NAME, value)
            }
        }

    override var profileUrl: String?
        get() = sharedPreferences.getString(KEY_PROFILE_URL, "") ?: ""
        set(value) {
            sharedPreferences.edit(commit = true) {
                putString(KEY_PROFILE_URL, value)
            }
        }

    override var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        set(value) {
            sharedPreferences.edit(commit = true) {
                putString(KEY_EMAIL, value)
            }
        }

    override var coin: Int?
        get() = sharedPreferences.getInt(KEY_COIN, 0)
        set(value) {
            sharedPreferences.edit(commit = true) {
                putInt(KEY_COIN, value ?: 0)
            }
        }

    companion object {
        private const val KEY_USER_ID = "pref-user-id"
        private const val KEY_DISPLAY_NAME = "pref-display-name"
        private const val KEY_PROFILE_URL = "pref-profile-url"
        private const val KEY_EMAIL = "pref-email"
        private const val KEY_COIN = "pref-coin"
    }

}
