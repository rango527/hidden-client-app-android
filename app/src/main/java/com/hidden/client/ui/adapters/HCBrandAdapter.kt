package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCBrandBinding
import com.hidden.client.ui.viewholders.HCBrandVH
import com.hidden.client.ui.viewmodels___.HCBrandViewModel

class HCBrandAdapter(
    private val list: ArrayList<HCBrandViewModel>) : RecyclerView.Adapter<HCBrandVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCBrandVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val brandBinding: HCBrandBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_brand, parent, false)

        return HCBrandVH(brandBinding)
    }

    override fun onBindViewHolder(holder: HCBrandVH, position: Int) {

        val brandModel = list[position]
        holder.bind(brandModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}