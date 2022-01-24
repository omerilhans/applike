package com.omer.ilhanli.applike.tool

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.omer.ilhanli.applike.R
import com.omer.ilhanli.applike.data.model.Position
import java.util.*

@BindingAdapter("android:visibility")
fun View.visibility(visible: Boolean = false) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:format")
fun TextView.bind(position: Position? = null) {
    position?.let {
        text = context.getString(R.string.text_last_position).format(it.posX.toString(), it.posY.toString())

        setTextColor(Color.GREEN)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                setTextColor(Color.GRAY)
            }
        }, 250)
    }
}
