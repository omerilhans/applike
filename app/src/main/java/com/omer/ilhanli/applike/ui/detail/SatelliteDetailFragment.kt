package com.omer.ilhanli.applike.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.omer.ilhanli.applike.R
import com.omer.ilhanli.applike.data.state.State
import com.omer.ilhanli.applike.databinding.FragmentSatelliteDetailBinding
import com.omer.ilhanli.applike.tool.observeOn
import com.omer.ilhanli.applike.tool.toastOn
import com.omer.ilhanli.applike.ui.base.BaseFrg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailFragment : BaseFrg<FragmentSatelliteDetailBinding>(R.layout.fragment_satellite_detail) {

    private val viewModel: SatelliteDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            extractBundle(arguments) { id ->
                observeOn(fetchSatellitePosition(id)) { // first get position of satellite from service ***
                    if (it.state == State.COMPLETE) {
                        observeOn(listenRandomPosition()) { position -> // then get position from list in memory for per 3 second #-#-#..
                            randomPosition.postValue(position)
                        }
                    }
                }.also {
                    observeOn(error) { message ->
                        requireContext() toastOn message
                    }
                }
            }
        }.also {
            binding.viewModel = it
        }

    }

}