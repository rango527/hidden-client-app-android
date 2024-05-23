package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateWorkExperienceItemBinding
import com.hidden.client.models.entity.WorkExperienceEntity
import com.hidden.client.ui.viewmodels.main.WorkExperienceViewVM

class WorkExperienceListAdapter: RecyclerView.Adapter<WorkExperienceListAdapter.ViewHolder>() {

    private lateinit var experienceList: List<WorkExperienceEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CandidateWorkExperienceItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_candidate_work_experience, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(experienceList[position])
    }

    override fun getItemCount(): Int {
        return if(::experienceList.isInitialized) experienceList.size else 0
    }

    fun updateWorkExperienceList(experienceList: List<WorkExperienceEntity>){
        this.experienceList = experienceList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateWorkExperienceItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = WorkExperienceViewVM()

        fun bind(experience: WorkExperienceEntity){
            viewModel.bind(experience)
            binding.viewModel = viewModel
        }
    }
}