package com.omer.ilhanli.applike.data.source

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePositionList
import com.omer.ilhanli.applike.data.source.SatelliteDataSource.Companion.gson
import com.omer.ilhanli.applike.tool.ToolPreferences
import com.omer.ilhanli.applike.tool.ToolPreferences.Companion.KEY_PREF_DETAIL_LIST
import com.omer.ilhanli.applike.tool.ToolPreferences.Companion.KEY_PREF_LIST
import com.omer.ilhanli.applike.tool.ToolPreferences.Companion.KEY_PREF_POSITION_LIST
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalSatelliteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val sharedPref: ToolPreferences
) : SatelliteDataSource {

    override fun satelliteList(save: Boolean, list: ArrayList<Satellite>?): ArrayList<Satellite>? {
        if (save) {
            set(KEY_PREF_LIST, list).also {
                return null
            }
        }

        return get(KEY_PREF_LIST, typeToken = object : TypeToken<ArrayList<Satellite>>() {})
    }

    override fun satelliteDetailList(save: Boolean, list: ArrayList<SatelliteDetail>?): ArrayList<SatelliteDetail>? {
        if (save) {
            set(KEY_PREF_DETAIL_LIST, list).also {
                return null
            }
        }

        return get(KEY_PREF_DETAIL_LIST, typeToken = object : TypeToken<ArrayList<SatelliteDetail>>() {})
    }

    override fun satellitePositionList(save: Boolean, satellitePositionList: SatellitePositionList?): SatellitePositionList? {
        if (save) {
            set(KEY_PREF_POSITION_LIST, satellitePositionList).also {
                return null
            }
        }

        return get(KEY_PREF_POSITION_LIST, classType = SatellitePositionList::class.java)
    }

    override fun includeLocally(key: String): Boolean {
        return sharedPref.isContain(key)
    }

    private fun <T> set(key: String, value: T?) { // write object
        val jsnStr = gson.toJson(value)
        sharedPref.setString(key, jsnStr)
    }

    private fun <T> get(key: String, typeToken: TypeToken<*>? = null, classType: Class<*>? = null): T? { // read object
        val jsnStr = sharedPref.getString(key)
        if (typeToken == null) {
            gson.fromJson<T>(jsnStr, classType).also {
                return it
            }
        } else {
            gson.fromJson<T>(jsnStr, typeToken.type).also {
                return it
            }
        }
    }
}