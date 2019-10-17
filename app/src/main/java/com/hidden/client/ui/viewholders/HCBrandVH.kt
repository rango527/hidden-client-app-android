package com.hidden.client.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.HCBrandBinding
import com.hidden.client.databinding.HCCandidateBinding
import com.hidden.client.ui.viewmodels.HCBrandViewModel
import com.hidden.client.ui.viewmodels.HCCandidateViewModel

class HCBrandVH(private val brandBinding: HCBrandBinding) : RecyclerView.ViewHolder(brandBinding.root) {

    fun bind (brandViewModel: HCBrandViewModel) {
        this.brandBinding.brandModel = brandViewModel
        brandBinding.executePendingBindings()
    }
}