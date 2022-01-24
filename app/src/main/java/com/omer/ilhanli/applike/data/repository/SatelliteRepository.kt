package com.omer.ilhanli.applike.data.repository

import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePosition
import com.omer.ilhanli.applike.data.model.SatellitePositionList
import com.omer.ilhanli.applike.data.state.Source
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {
    fun fetchSatelliteList(): Flow<Source<ArrayList<Satellite>>>
    fun searchSatelliteByName(query: String): Flow<Source<ArrayList<Satellite>>>
    fun getSatelliteDetailById(id: Int): Flow<Source<SatelliteDetail>>
    fun getSatellitePosition(id: Int): Flow<Source<SatellitePosition>>

    fun saveSatelliteList(satelliteList: ArrayList<Satellite>? = null)
    fun saveSatelliteDetailList(satelliteDetailList: ArrayList<SatelliteDetail>? = null)
    fun saveSatellitePositionList(satellitePositionList: SatellitePositionList? = null)

    fun includeLocally(key: String): Boolean {
        return false
    }
}