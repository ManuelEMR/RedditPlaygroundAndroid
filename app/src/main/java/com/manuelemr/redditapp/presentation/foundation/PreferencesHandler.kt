package com.manuelemr.redditapp.presentation.foundation

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHandler(private val prefs: SharedPreferences) {

    var token: String?
        set(value) = prefs.edit {
            putString("token", value)
        }
        get() = prefs.getString("token", null)
}