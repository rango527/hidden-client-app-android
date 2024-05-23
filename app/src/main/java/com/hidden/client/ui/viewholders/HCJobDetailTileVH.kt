package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCJobDetailTileBinding
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel

class HCJobDetailTileVH(private val jobDetailTileBinding: HCJobDetailTileBinding) : RecyclerView.ViewHolder(jobDetailTileBinding.root) {

    fun bind (jobDetailTileViewModel: HCJobDetailTileViewModel) {
        this.jobDetailTileBinding.jobDetailTileModel = jobDetailTileViewModel
        jobDetailTileBinding.executePendingBindings()
    }
}