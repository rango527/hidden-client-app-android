package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCWorkExperienceBinding
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel

class HCWorkExperienceVH(private val workExperienceBinding: HCWorkExperienceBinding) : RecyclerView.ViewHolder(workExperienceBinding.root) {

    fun bind (workExperienceViewModel: HCWorkExperienceViewModel) {
        this.workExperienceBinding.workExperienceModel = workExperienceViewModel
        workExperienceBinding.executePendingBindings()
    }
}