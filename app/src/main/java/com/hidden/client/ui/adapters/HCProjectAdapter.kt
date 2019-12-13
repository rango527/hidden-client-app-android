package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCBrandBinding
import com.hidden.client.databinding.HCProjectBinding
import com.hidden.client.ui.viewholders.HCBrandVH
import com.hidden.client.ui.viewholders.HCProjectVH
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCProjectViewModel

class HCProjectAdapter(
    private val context:Context,
    private val list: ArrayList<HCProjectViewModel>) : RecyclerView.Adapter<HCProjectVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCProjectVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val projectBinding: HCProjectBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_project, parent, false)

        return HCProjectVH(projectBinding)
    }

    override fun onBindViewHolder(holder: HCProjectVH, position: Int) {

        val projectModel = list[position]
        holder.bind(projectModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}