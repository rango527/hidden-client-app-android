package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.activities.settings.CandidateDetailActivity
import com.hidden.client.ui.activities.settings.CandidateListActivity
import com.hidden.client.ui.viewmodels.main.CandidateViewVM

class CandidateListAdapter: RecyclerView.Adapter<CandidateListAdapter.ViewHolder>() {

    private lateinit var candidateList: List<CandidateEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CandidateItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_candidate, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(candidateList[position])

        holder.itemView.setOnClickListener {

            val candidate = candidateList[position]

            val intent = Intent(HCGlobal.getInstance().currentActivity, CandidateDetailActivity::class.java)
            intent.putExtra("category_id", candidate.id.toString())
            HCGlobal.getInstance().currentActivity.startActivity(intent)
            (HCGlobal.getInstance().currentActivity as CandidateListActivity).overridePendingVTransitionEnter()
        }
    }

    override fun getItemCount(): Int {
        return if(::candidateList.isInitialized) candidateList.size else 0
    }

    fun updateCandidateList(candidateList: List<CandidateEntity>){
        this.candidateList = candidateList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = CandidateViewVM()

        fun bind(candidate: CandidateEntity){
            viewModel.bind(candidate)
            binding.viewModel = viewModel
        }
    }
}