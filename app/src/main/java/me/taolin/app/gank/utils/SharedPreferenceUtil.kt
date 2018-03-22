package me.taolin.app.gank.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import me.taolin.app.gank.App

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/23
 * @description
 */
class SharedPreferenceUtil {

    companion object {

        private fun getSharedPreference(): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(App.instance)
        }

        fun writeInt(key: String, value: Int) {
            getSharedPreference().edit().putInt(key, value).apply()
        }

        fun readInt(key: String, defValue: Int): Int {
            return getSharedPreference().getInt(key, defValue)
        }

        fun writeString(key: String, value: String) {
            getSharedPreference().edit().putString(key, value).apply()
        }

        fun readString(key: String, defValue: String): String {
            return getSharedPreference().getString(key, defValue)
        }

        fun writeBoolean(key: String, value: Boolean) {
            getSharedPreference().edit().putBoolean(key, value).apply()
        }

        fun readBoolean(key: String, defValue: Boolean): Boolean {
            return getSharedPreference().getBoolean(key, defValue)
        }
    }
}