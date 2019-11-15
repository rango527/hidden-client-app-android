package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCProcessBinding
import com.hidden.client.ui.viewmodels___.HCProcessViewModel

class HCProcessVH(private val processBinding: HCProcessBinding) : RecyclerView.ViewHolder(processBinding.root) {

    fun bind (processViewModel: HCProcessViewModel) {
        this.processBinding.processModel = processViewModel
        processBinding.executePendingBindings()
    }
}