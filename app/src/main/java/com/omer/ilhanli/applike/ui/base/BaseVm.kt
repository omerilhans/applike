package com.omer.ilhanli.applike.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseVm : ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()

    var empty: MutableLiveData<Boolean> = MutableLiveData(false)

    var error = MutableLiveData<String>()
}