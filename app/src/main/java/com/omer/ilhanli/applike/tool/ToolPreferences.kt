package com.omer.ilhanli.applike.tool

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        const val KEY_PREF_LIST = "satellite_list"
        const val KEY_PREF_DETAIL_LIST = "satellite_detail_list"
        const val KEY_PREF_POSITION_LIST = "positions"
    }

    private var shaPre: SharedPreferences? =
        context.getSharedPreferences("satellite.sp", Context.MODE_PRIVATE)

    // GETTERS
    fun getString(key: String): String? =
        if (shaPre?.contains(key) == true)
            shaPre?.getString(key, "")
        else
            null

    fun isContain(key: String): Boolean {
        return shaPre?.contains(key) ?: false
    }

    // SETTERS
    fun setString(key: String, value: String?) {
        val editor = shaPre?.edit()
        value.let { editor?.putString(key, value) } ?: remove(key)
        editor?.apply()
    }

    // REMOVE
    private fun remove(key: String) {
        val editor = shaPre?.edit()
        editor?.remove(key)
        editor?.apply()
    }

}