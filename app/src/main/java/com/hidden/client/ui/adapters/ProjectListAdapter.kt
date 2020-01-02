package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateProjectItemBinding
import com.hidden.client.models.ProjectEntity
import com.hidden.client.ui.viewmodels.main.ProjectViewVM

class ProjectListAdapter: RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    private lateinit var projectList: List<ProjectEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListAdapter.ViewHolder {
        val binding: CandidateProjectItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_candidate_highlight, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectListAdapter.ViewHolder, position: Int) {
        holder.bind(projectList[position])
    }

    override fun getItemCount(): Int {
        return if(::projectList.isInitialized) projectList.size else 0
    }

    fun updateProjectList(projectList: List<ProjectEntity>){
        this.projectList = projectList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateProjectItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = ProjectViewVM()

        fun bind(project: ProjectEntity){
            viewModel.bind(project)
            binding.viewModel = viewModel
        }
    }
}