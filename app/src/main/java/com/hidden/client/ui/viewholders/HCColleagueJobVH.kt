package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCColleagueJobBinding
import com.hidden.client.ui.viewmodels.HCColleagueJobViewModel

class HCColleagueJobVH(private val jobBinding: HCColleagueJobBinding): RecyclerView.ViewHolder(jobBinding.root) {

    fun bind (jobViewModel: HCColleagueJobViewModel) {
        this.jobBinding.jobModel = jobViewModel
        jobBinding.executePendingBindings()
    }

}