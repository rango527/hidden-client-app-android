package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateProjectLgItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ProjectEntity
import com.hidden.client.ui.activities.ProjectDetailActivity
import com.hidden.client.ui.viewmodels.main.ProjectViewVM

class ProjectListLgAdapter: RecyclerView.Adapter<ProjectListLgAdapter.ViewHolder>() {

    private lateinit var projectList: List<ProjectEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CandidateProjectLgItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_candidate_lg_highlight, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(projectList[position])

        holder.itemView.setOnClickListener {
            HCGlobal.getInstance().currentProjectIndex = position
            val intent = Intent(HCGlobal.getInstance().currentActivity, ProjectDetailActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if(::projectList.isInitialized) projectList.size else 0
    }

    fun updateProjectList(projectList: List<ProjectEntity>){
        this.projectList = projectList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateProjectLgItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = ProjectViewVM()

        fun bind(project: ProjectEntity){
            viewModel.bind(project)
            binding.viewModel = viewModel
        }
    }
}