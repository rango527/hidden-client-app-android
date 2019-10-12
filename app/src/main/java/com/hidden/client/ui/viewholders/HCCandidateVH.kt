package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCCandidateBinding
import com.hidden.client.ui.viewmodels.HCCandidateViewModel

class HCCandidateVH(private val candidateBinding: HCCandidateBinding) : RecyclerView.ViewHolder(candidateBinding.root) {

    fun bind (candidateViewModel: HCCandidateViewModel) {
        this.candidateBinding.candidateModel = candidateViewModel
        candidateBinding.executePendingBindings()
    }
}