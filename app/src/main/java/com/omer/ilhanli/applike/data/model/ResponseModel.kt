package com.omer.ilhanli.applike.data.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import com.omer.ilhanli.applike.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Satellite(
    val id: Int? = null,
    val active: Boolean? = null,
    val name: String? = null,
) : BaseModel(), Parcelable {

    fun textState(): String = if (active == true) "Active" else "Passive"

    fun srcState(context: Context): Drawable? =
        if (active == true) ContextCompat.getDrawable(context, R.drawable.bg_circle_state_active)
        else ContextCompat.getDrawable(context, R.drawable.bg_circle_state_passive)
}

@Parcelize
data class SatelliteDetail(
    val id: Int? = null,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Long? = null,
    @SerializedName("first_flight")
    val firstFlight: String? = null,
    val height: Int? = null,
    val mass: Long? = null,
) : BaseModel(), Parcelable

@Parcelize
data class SatellitePosition(
    val id: String? = null,
    val positions: ArrayList<Position>? = null
) : BaseModel(), Parcelable

@Parcelize
data class SatellitePositionList(
    val list: ArrayList<SatellitePosition>? = null
) : Parcelable

@Parcelize
data class Position(
    val posX: Double? = null,
    val posY: Double? = null,
) : Parcelable

open class BaseModel