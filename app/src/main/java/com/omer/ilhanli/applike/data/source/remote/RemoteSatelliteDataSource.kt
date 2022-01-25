package com.omer.ilhanli.applike.data.source.remote

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePositionList
import com.omer.ilhanli.applike.data.source.SatelliteDataSource
import com.omer.ilhanli.applike.tool.Constant.String.KEY_DETAIL
import com.omer.ilhanli.applike.tool.Constant.String.KEY_LIST
import com.omer.ilhanli.applike.tool.Constant.String.KEY_POSITIONS
import com.omer.ilhanli.applike.tool.ToolGson.jsonFrom
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteSatelliteDataSource @Inject constructor(@ApplicationContext val context: Context) : SatelliteDataSource {

    override fun satelliteList(save: Boolean, list: ArrayList<Satellite>?): ArrayList<Satellite>? {
        return fetch(context jsonFrom KEY_LIST, typeToken = object : TypeToken<ArrayList<Satellite>>() {})
    }

    override fun satelliteDetailList(save: Boolean, list: ArrayList<SatelliteDetail>?): ArrayList<SatelliteDetail>? {
        return fetch(context jsonFrom KEY_DETAIL, typeToken = object : TypeToken<ArrayList<SatelliteDetail>>() {})
    }

    override fun satellitePositionList(save: Boolean, satellitePositionList: SatellitePositionList?): SatellitePositionList? {
        return fetch(context jsonFrom KEY_POSITIONS, classType = SatellitePositionList::class.java)
    }
}
