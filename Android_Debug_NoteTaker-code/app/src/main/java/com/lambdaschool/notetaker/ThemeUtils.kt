package com.lambdaschool.notetaker

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager

object ThemeUtils {
    internal fun getSelectedTheme(activity: Activity): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(
                activity.applicationContext)
        val specialTheme = preferences.getBoolean(
                activity.resources.getString(
                        R.string.theme_switch_key),
                false)
        return if (specialTheme) R.style.AppThemeRound else R.style.AppTheme
    }

    fun onActivityCreateSetTheme(activity: Activity) {
        activity.setTheme(getSelectedTheme(activity))
    }

    fun refreshActivity(activity: Activity) {
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    }

    fun checkTheme(activity: Activity, activeTheme: Int): Boolean {
        return if (getSelectedTheme(activity) == activeTheme) {
            true
        } else {
            false
        }
    }
}
