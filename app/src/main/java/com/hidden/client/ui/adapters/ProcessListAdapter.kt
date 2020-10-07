package com.hidden.client.ui.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.ProcessItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.Utility
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.ProcessStageEntity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.main.ProcessViewVM

class ProcessListAdapter: RecyclerView.Adapter<ProcessListAdapter.ViewHolder>() {

    private lateinit var processList: List<ProcessEntity>
    private lateinit var processStageList: List<ProcessStageEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProcessItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_process, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(processList[position])

        // get unread message count
        val processs = processList[position]
        val processMessageUnreadNumber = processs.messageUnreadNumber
        val showUnreadmessage: ConstraintLayout = holder.itemView.findViewById(R.id.unreadmessage)
        val showUnreadmessageCount: TextView = holder.itemView.findViewById(R.id.text_unreadmessage_count)

        // show unread message count
        if (processMessageUnreadNumber != 0) {
            showUnreadmessage.visibility = View.VISIBLE
            showUnreadmessageCount.text = processMessageUnreadNumber.toString()
        } else {
            showUnreadmessage.visibility = View.GONE
        }

        // get stage process
        val stageList = processs.getStageList()

        val initStage = stageList[0]
        val firstInterviewStage = stageList[1]
        val furtherInterviewStage = stageList[2]
        val finalInterviewStage = stageList[3]
        val offerStage = stageList[4]
        val offerAcceptedStage = stageList[5]
        val startedStage = stageList[6]

        val layoutInit: ImageView = holder.itemView.findViewById(R.id.img_stage_init)
        layoutInit.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(initStage.clientTileBackgroundColor))

        val layoutFirstInterview: ImageView = holder.itemView.findViewById(R.id.img_stage_first_interview)
        layoutFirstInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(firstInterviewStage.clientTileBackgroundColor))

        val layoutFurtherInterview: ImageView = holder.itemView.findViewById(R.id.img_stage_further_interview)
        layoutFurtherInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(furtherInterviewStage.clientTileBackgroundColor))

        val layoutFinalInterview: ImageView = holder.itemView.findViewById(R.id.img_stage_final_interview)
        layoutFinalInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(finalInterviewStage.clientTileBackgroundColor))

        val layoutOfferStage: ImageView = holder.itemView.findViewById(R.id.img_stage_offer_stage)
        layoutOfferStage.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(offerStage.clientTileBackgroundColor))

        val layoutOfferAcceptedStage: ImageView = holder.itemView.findViewById(R.id.img_stage_offer_accepted_stage)
        layoutOfferAcceptedStage.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(offerAcceptedStage.clientTileBackgroundColor))

        val layoutStarted: ImageView = holder.itemView.findViewById(R.id.img_stage_started)
        layoutStarted.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(startedStage.clientTileBackgroundColor))

        holder.itemView.setOnClickListener {
            val process = processList[position]

            val intent = Intent(HCGlobal.getInstance().currentActivity, ProcessActivity::class.java)
            intent.putExtra("processId", process.id)
            intent.putExtra("conversationId", process.conversationId)
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