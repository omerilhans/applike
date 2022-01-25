package com.omer.ilhanli.applike.data.repository

import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePosition
import com.omer.ilhanli.applike.data.model.SatellitePositionList
import com.omer.ilhanli.applike.data.state.Source
import com.omer.ilhanli.applike.data.source.SatelliteDataSource
import com.omer.ilhanli.applike.di.LocalDataSource
import com.omer.ilhanli.applike.di.RemoteDataSource
import com.omer.ilhanli.applike.tool.Constant
import com.omer.ilhanli.applike.tool.Constant.String.ERROR
import com.omer.ilhanli.applike.data.source.local.SharedPreferences.Companion.KEY_PREF_DETAIL_LIST
import com.omer.ilhanli.applike.data.source.local.SharedPreferences.Companion.KEY_PREF_LIST
import com.omer.ilhanli.applike.data.source.local.SharedPreferences.Companion.KEY_PREF_POSITION_LIST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

@Singleton
class SatelliteRepositoryImpl @Inject constructor(@RemoteDataSource var remoteDataSource: SatelliteDataSource, @LocalDataSource var localDataSource: SatelliteDataSource) : SatelliteRepository {

    override fun fetchSatelliteList(): Flow<Source<ArrayList<Satellite>>> = flow {
        try {
            delay(Constant.Numeric.DURATION_1_SECOND)
            val satelliteList = if (includeLocally(KEY_PREF_LIST)) {
                localDataSource.satelliteList()
            } else {
                remoteDataSource.satelliteList()
            }

            satelliteList?.let {
                emit(Source.complete(it))
                if (includeLocally(KEY_PREF_LIST).not()) {
                    saveSatelliteList(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
                .also { emit(Source.failure(value = null, message = e.message ?: ERROR)) }
        }
    }

    override fun getSatelliteDetailById(id: Int): Flow<Source<SatelliteDetail>> = flow {
        try {
            val satelliteDetailList = if (includeLocally(KEY_PREF_DETAIL_LIST)) {
                localDataSource.satelliteDetailList()
            } else {
                delay(Constant.Numeric.DURATION_1_SECOND)
                remoteDataSource.satelliteDetailList()
            }

            satelliteDetailList
                ?.also {
                    if (includeLocally(KEY_PREF_DETAIL_LIST).not()) {
                        saveSatelliteDetailList(it) //
                    }
                }?.find { it.id == id }
                ?.let {
                    emit(Source.complete(it))
                }
        } catch (e: Exception) {
            e.printStackTrace()
                .also { emit(Source.failure(value = null, message = e.message ?: ERROR)) }
        }
    }

    override fun searchSatelliteByName(query: String): Flow<Source<ArrayList<Satellite>>> = flow {
        try {
            val satelliteList = if (includeLocally(KEY_PREF_LIST)) {
                localDataSource.satelliteList()
            } else {
                remoteDataSource.satelliteList()
            }

            satelliteList
                ?.also {
                    it.filter { satellite ->
                        satellite.name?.lowercase(Locale.getDefault())?.contains(query) ?: false
                    }.also {
                        emit(Source.complete(value = it as ArrayList<Satellite>))
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
                .also { emit(Source.failure(value = null, message = e.message ?: ERROR)) }
        }
    }

    override fun getSatellitePosition(id: Int): Flow<Source<SatellitePosition>> = flow {
        try {
            delay(Constant.Numeric.DURATION_1_SECOND)
            val satellitePositionList = if (includeLocally(KEY_PREF_POSITION_LIST)) {
                localDataSource.satellitePositionList()
            } else {
                remoteDataSource.satellitePositionList()
            }

            satellitePositionList
                ?.apply {
                    if (includeLocally(KEY_PREF_POSITION_LIST).not()) {
                        saveSatellitePositionList(this)
                    }
                }.also { list ->
                    list?.list?.find { it.id?.toInt() ?: 0 == id }
                        ?.run {
                            emit(Source.complete(this))
                        }
                }

        } catch (e: Exception) {
            e.printStackTrace()
                .also { emit(Source.failure(value = null, message = e.message ?: ERROR)) }
        }
    }

    override fun saveSatelliteList(satelliteList: ArrayList<Satellite>?) {
        localDataSource.satelliteList(true, satelliteList)
    }

    override fun saveSatelliteDetailList(satelliteDetailList: ArrayList<SatelliteDetail>?) {
        localDataSource.satelliteDetailList(true, satelliteDetailList)
    }

    override fun saveSatellitePositionList(satellitePositionList: SatellitePositionList?) {
        localDataSource.satellitePositionList(true, satellitePositionList)
    }

    override fun includeLocally(key: String): Boolean {
        return localDataSource.includeLocally(key)
    }
}