package com.hidden.client.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.JobItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.ui.viewmodels.main.ProcessViewVM

class JobFilterAdapter: RecyclerView.Adapter<JobFilterAdapter.ViewHolder>() {

    private lateinit var processList: List<ProcessEntity>
    private lateinit var imgTickJob: ImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: JobItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_job, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(processList[position])

        val temp = HCGlobal.getInstance().getJobPick[position]

        val processs = processList[position]

        val jobId = processs.jobId
        val jobTitle = processs.jobTitle
        val jobCityName = processs.jobCityName

        val textViewJob: TextView = holder.itemView.findViewById(R.id.textview)
        textViewJob.text = "$jobTitle, $jobCityName"

        holder.itemView.setOnClickListener {
            imgTickJob = holder.itemView.findViewById(R.id.img_tick_job)

            if (temp.jobTick)
                imgTickJob.setImageResource(R.drawable.tick_off)
            else
                imgTickJob.setImageResource(R.drawable.tick_on)

            temp.jobTick = !temp.jobTick
        }
    }

    override fun getItemCount(): Int {
        return if(::processList.isInitialized) processList.size else 0
    }

    fun updateProcessList(processList: List<ProcessEntity>){
        this.processList = processList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: JobItemBinding): RecyclerView.ViewHolder(binding.root){

        private val viewModel = ProcessViewVM()

        fun bind(process: ProcessEntity){
            viewModel.bind(process)
            binding.viewModel = viewModel
        }
    }
}