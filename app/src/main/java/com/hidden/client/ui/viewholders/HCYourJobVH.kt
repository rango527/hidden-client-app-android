package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCYourJobBinding
import com.hidden.client.ui.viewmodels.HCYourJobViewModel

class HCYourJobVH(private val jobBinding: HCYourJobBinding): RecyclerView.ViewHolder(jobBinding.root) {

    fun bind (jobViewModel: HCYourJobViewModel) {
        this.jobBinding.jobModel = jobViewModel
        jobBinding.executePendingBindings()
    }

}