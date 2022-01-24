package com.omer.ilhanli.applike.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omer.ilhanli.applike.R
import com.omer.ilhanli.applike.data.model.Satellite
import com.omer.ilhanli.applike.databinding.ItemSatelliteBinding

@SuppressLint("NotifyDataSetChanged")
class SatelliteRecyclerAdapter(
    private val clickListener: ((Satellite) -> Unit)? = null,
    private var listSatellite: ArrayList<Satellite>? = null
) : RecyclerView.Adapter<SatelliteRecyclerAdapter.SatelliteViewHolder>() {

    inner class SatelliteViewHolder(private val binding: ItemSatelliteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Satellite) {
            binding.apply {
                this.item = item
                root.setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
        }
    }

    infix fun updateOn(newList: ArrayList<Satellite>?) {
        newList?.let {
            if (it.isNotEmpty()) {
                listSatellite = newList
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        DataBindingUtil.inflate<ItemSatelliteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_satellite,
            parent,
            false
        ).also {
            return SatelliteViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        listSatellite?.let { list ->
            holder.bind(list[position])
        }
    }

    override fun getItemCount() = listSatellite?.size ?: 0

}