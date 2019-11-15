package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateItemBinding
import com.hidden.client.models.Candidate
import com.hidden.client.ui.viewmodels.main.CandidateViewVM

class CandidateListAdapter: RecyclerView.Adapter<CandidateListAdapter.ViewHolder>() {

    private lateinit var candidateList: List<Candidate>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateListAdapter.ViewHolder {
        val binding: CandidateItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_row_candidate, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CandidateListAdapter.ViewHolder, position: Int) {
        holder.bind(candidateList[position])
    }

    override fun getItemCount(): Int {
        return if(::candidateList.isInitialized) candidateList.size else 0
    }

    fun updateCandidateList(postList:List<Candidate>){
        this.candidateList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = CandidateViewVM()

        fun bind(candidate: Candidate){
            viewModel.bind(candidate)
            binding.viewModel = viewModel
        }
    }
}