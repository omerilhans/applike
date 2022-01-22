package com.omer.ilhanli.applike.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFrg<B : ViewDataBinding>(@LayoutRes val layoutResId: Int) : Fragment() {

    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<B>(layoutInflater, layoutResId, container, false)
            .apply {
                binding = this
                binding.lifecycleOwner = viewLifecycleOwner
            }.also {
                return it.root
            }
    }


}