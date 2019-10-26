package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCJobBinding
import com.hidden.client.ui.viewmodels.HCJobViewModel

class HCJobVH(private val jobBinding: HCJobBinding): RecyclerView.ViewHolder(jobBinding.root) {

    fun bind (jobViewModel: HCJobViewModel) {
        this.jobBinding.jobModel = jobViewModel
        jobBinding.executePendingBindings()
    }

}