package com.omer.ilhanli.applike.ui.list

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.omer.ilhanli.applike.R
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.databinding.FragmentSatelliteListBinding
import com.omer.ilhanli.applike.tool.*
import com.omer.ilhanli.applike.tool.Constant.String.KEY_SATELLITE
import com.omer.ilhanli.applike.tool.Constant.String.KEY_SATELLITE_DETAIL
import com.omer.ilhanli.applike.ui.base.BaseFrg
import com.omer.ilhanli.applike.ui.detail.SatelliteRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteListFragment : BaseFrg<FragmentSatelliteListBinding>(R.layout.fragment_satellite_list) {

    private val viewModel: SatelliteListViewModel by viewModels()
    private val adapter = SatelliteRecyclerAdapter(::itemClicked, ArrayList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observe()
    }

    private fun bind() {
        binding.viewModel = viewModel
        binding.recyclerSatelliteList setOn adapter
        binding.etSearchQuery watchOn { text ->
            search(text)
        }
    }

    private fun observe() {
        with(viewModel) {
            if (satellitesFirstLoad.value.isNullOrEmpty()) {
                observeOn(fetchSatelliteList()) {
                    adapter updateOn it.value // update new list
                }

                observeOn(error) { message ->
                    requireContext() toastOn message // pop up
                }
            } else {
                adapter updateOn satellitesFirstLoad.value
            }
        }
    }

    @VisibleForTesting
    fun search(text: String) {
        if (text.isEmpty()) {
            adapter updateOn viewModel.satellitesFirstLoad.value
        } else {
            observeOn(viewModel.searchSatelliteName(text)) { // search query
                adapter updateOn it.value
            }
        }
    }

    private fun itemClicked(satellite: Satellite) {
        observeOn(viewModel.fetchSatelliteDetailById(satellite.id ?: 0)) { source ->

            source.value?.let { satelliteDetail -> // navigate to detail
                navigateOn(
                    R.id.action_listScreen_to_detailScreen,
                    bundleOf(KEY_SATELLITE to satellite, KEY_SATELLITE_DETAIL to satelliteDetail)
                )
            }
        }
    }

}