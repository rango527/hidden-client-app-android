package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCProjectBinding
import com.hidden.client.ui.viewmodels___.HCProjectViewModel

class HCProjectVH(private val projectBinding: HCProjectBinding) : RecyclerView.ViewHolder(projectBinding.root) {

    fun bind (projectViewModel: HCProjectViewModel) {
        this.projectBinding.projectModel = projectViewModel
        projectBinding.executePendingBindings()
    }
}