package com.omer.ilhanli.applike.ui.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.omer.ilhanli.applike.data.model.Position
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.model.SatelliteDetail
import com.omer.ilhanli.applike.data.model.SatellitePosition
import com.omer.ilhanli.applike.data.repository.SatelliteRepository
import com.omer.ilhanli.applike.data.state.Source
import com.omer.ilhanli.applike.data.state.State
import com.omer.ilhanli.applike.tool.Constant
import com.omer.ilhanli.applike.tool.Constant.String.ERROR
import com.omer.ilhanli.applike.ui.base.BaseVm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.omer.ilhanli.applike.tool.Constant.String.KEY_SATELLITE
import com.omer.ilhanli.applike.tool.Constant.String.KEY_SATELLITE_DETAIL

@HiltViewModel
class SatelliteDetailViewModel
@Inject constructor(private val satelliteRepository: SatelliteRepository) : BaseVm() {

    var satellitePosition: MutableLiveData<SatellitePosition>? = MutableLiveData<SatellitePosition>()
    var satellite = MutableLiveData<Satellite>()
    var satelliteDetail = MutableLiveData<SatelliteDetail>()
    var randomPosition: MutableLiveData<Position> = MutableLiveData()

    fun extractBundle(args: Bundle?, give: (Int) -> Unit) {
        args?.let {
            satellite.value = it.getParcelable(KEY_SATELLITE)
            satelliteDetail.postValue(it.getParcelable(KEY_SATELLITE_DETAIL))
            give(satellite.value?.id ?: -1)
        }
    }

    fun fetchSatellitePosition(id: Int): LiveData<Source<SatellitePosition>> =
        satelliteRepository.getSatellitePosition(id)
            .onStart { emit(Source.progress()) }
            .map { prepareSourceState(it) }
            .asLiveData()

    fun listenRandomPosition(): LiveData<Position> = flow {
        try {
            while (true) {
                delay(Constant.Numeric.DURATION_3_SECOND)
                satellitePosition?.value?.positions?.random()
                    ?.also { position ->
                        emit(position)
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.asLiveData()

    private fun prepareSourceState(source: Source<SatellitePosition>): Source<SatellitePosition> {
        loading.value = source.state == State.PROGRESS
        when (source.state) {
            State.COMPLETE -> {
                source.value?.let {
                    satellitePosition?.value = it
                        .also {
                            randomPosition.value = it.positions?.random()
                        }
                }
            }
            State.FAILURE -> {
                error.value = "$ERROR ${source.message}"
            }
            else -> {}
        }

        return source
    }

}