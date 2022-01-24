package com.omer.ilhanli.applike.tool

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.omer.ilhanli.applike.ui.detail.SatelliteRecyclerAdapter

infix fun RecyclerView.setOn(adapter: SatelliteRecyclerAdapter) {
    itemAnimator = DefaultItemAnimator()
    setHasFixedSize(true)
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    this.adapter = adapter
}

infix fun EditText.watchOn(get: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            get(p0.toString().trim())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

infix fun Context.toastOn(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun <T> Fragment.observeOn(liveData: LiveData<T>, @NonNull observer: Observer<T>? = null) {
    observer?.let {
        liveData.observe(viewLifecycleOwner, it)
    }
}

fun Fragment.navigateOn(@IdRes actionResId: Int, @Nullable bundle: Bundle) = findNavController().navigate(actionResId, bundle)