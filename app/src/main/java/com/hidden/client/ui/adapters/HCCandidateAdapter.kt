package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCCandidateBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.settings.HCCandidateActivity
import com.hidden.client.ui.activities.settings.HCCandidateDetailActivity
import com.hidden.client.ui.viewholders.HCCandidateVH
import com.hidden.client.ui.viewmodels.HCCandidateViewModel

class HCCandidateAdapter(
    private val context:Context,
    private val list: ArrayList<HCCandidateViewModel>) : RecyclerView.Adapter<HCCandidateVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCCandidateVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val candidateBinding: HCCandidateBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_candidate, parent, false)

        return HCCandidateVH(candidateBinding)
    }

    override fun onBindViewHolder(holder: HCCandidateVH, position: Int) {

        val candidateModel = list[position]
        holder.bind(candidateModel)

        holder.itemView.setOnClickListener(View.OnClickListener {

            var candidate = candidateModel.getCandidate()

            val intent = Intent(context, HCCandidateDetailActivity::class.java)
            intent.putExtra("category_id", candidate.getCandiateId().toString())
            HCGlobal.getInstance().currentActivity.startActivity(intent)
            (HCGlobal.getInstance().currentActivity as HCCandidateActivity).overridePendingVTransitionEnter()
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}