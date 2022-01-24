package com.omer.ilhanli.applike.data.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePositionList

interface SatelliteDataSource {
    fun satelliteList(save: Boolean = false, list: ArrayList<Satellite>? = null): ArrayList<Satellite>?
    fun satelliteDetailList(save: Boolean = false, list: ArrayList<SatelliteDetail>? = null): ArrayList<SatelliteDetail>?
    fun satellitePositionList(
        save: Boolean = false,
        satellitePositionList: SatellitePositionList? = null
    ): SatellitePositionList?

    fun <Y> fetch(jsonString: String?, typeToken: TypeToken<*>? = null, classType: Class<*>? = null): Y? {
        if (typeToken == null) {
            gson.fromJson<Y>(jsonString, classType)
                .also {
                    return it
                }
        } else {
            gson.fromJson<Y>(jsonString, typeToken.type)
                .also {
                    return it
                }
        }
    }

    fun includeLocally(key: String): Boolean {
        return false
    }

    companion object {
        val gson = Gson()
    }
}