package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCMetricBinding
import com.hidden.client.ui.viewmodels.HCMetricViewModel

class HCMetricVH(private val metricBinding: HCMetricBinding) : RecyclerView.ViewHolder(metricBinding.root) {

    fun bind (metricViewModel: HCMetricViewModel) {
        this.metricBinding.metricModel = metricViewModel
        metricBinding.executePendingBindings()
    }
}