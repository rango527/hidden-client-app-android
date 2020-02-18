package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.ProcessItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.main.ProcessViewVM

class ProcessListAdapter: RecyclerView.Adapter<ProcessListAdapter.ViewHolder>() {

    private lateinit var processList: List<ProcessEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProcessItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_process, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(processList[position])

        holder.itemView.setOnClickListener {

            val process = processList[position]

            val intent = Intent(HCGlobal.getInstance().currentActivity, ProcessActivity::class.java)
            intent.putExtra("processId", process.id)
            intent.putExtra("jobId", process.jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if(::processList.isInitialized) processList.size else 0
    }

    fun updateProcessList(processList: List<ProcessEntity>){
        this.processList = processList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ProcessItemBinding): RecyclerView.ViewHolder(binding.root){

        private val viewModel = ProcessViewVM()

        fun bind(process: ProcessEntity){
            viewModel.bind(process)
            binding.viewModel = viewModel
        }
    }
}