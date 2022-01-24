package com.omer.ilhanli.applike.ui.list

import androidx.lifecycle.*
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.data.repository.SatelliteRepository
import com.omer.ilhanli.applike.data.state.Source
import com.omer.ilhanli.applike.data.state.State
import com.omer.ilhanli.applike.tool.Constant.String.ERROR
import com.omer.ilhanli.applike.ui.base.BaseVm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(private val satelliteRepository: SatelliteRepository) : BaseVm() {

    var satellites = MutableLiveData<ArrayList<Satellite>>()
    var satellitesFirstLoad = MutableLiveData<ArrayList<Satellite>>()

    fun fetchSatelliteList() = satelliteRepository.fetchSatelliteList()
        .onStart { emit(Source.progress()) }
        .map { prepareSourceState(it) }
        .asLiveData()

    fun searchSatelliteName(term: String) = satelliteRepository.searchSatelliteByName(term)
        .onStart { emit(Source.progress()) }
        .map { prepareSourceState(it) }
        .asLiveData()

    fun fetchSatelliteDetailById(id: Int) = satelliteRepository.getSatelliteDetailById(id)
        .onStart { emit(Source.progress()) }
        .asLiveData()

    private fun prepareSourceState(source: Source<ArrayList<Satellite>>): Source<ArrayList<Satellite>> {
        loading.value = source.state == State.PROGRESS
        when (source.state) {
            State.COMPLETE -> {
                source.value?.let {
                    satellites.value = it
                    if (satellitesFirstLoad.value.isNullOrEmpty()) {
                        satellitesFirstLoad.value = it
                    }
                }
                empty.value = source.value?.isNullOrEmpty()
            }
            State.FAILURE -> {
                error.value = "$ERROR ${source.message} "
                empty.value = true
            }
            else -> {}
        }

        return source
    }

}