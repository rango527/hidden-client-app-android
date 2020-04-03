package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCWorkExperienceBinding
import com.hidden.client.ui.viewholders.HCWorkExperienceVH
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel

class HCWorkExperienceAdapter(
    private val list: ArrayList<HCWorkExperienceViewModel>) : RecyclerView.Adapter<HCWorkExperienceVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCWorkExperienceVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val workExperienceBinding: HCWorkExperienceBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_work_experience, parent, false)

        return HCWorkExperienceVH(workExperienceBinding)
    }

    override fun onBindViewHolder(holder: HCWorkExperienceVH, position: Int) {

        val workExperienceModel = list[position]
        holder.bind(workExperienceModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}