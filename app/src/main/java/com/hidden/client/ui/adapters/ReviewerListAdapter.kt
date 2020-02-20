package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.databinding.ReviewerItemBinding
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.viewmodels.main.ReviewerViewVM
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.JobSettingActivity
import com.hidden.client.ui.activities.ProcessSettingActivity
import com.hidden.client.ui.activities.RemoveUserRoleActivity

class ReviewerListAdapter: RecyclerView.Adapter<ReviewerListAdapter.ViewHolder>() {

    private var jobProcessId: Int = 0
    private lateinit var reviewerList: ArrayList<ReviewerEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ReviewerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_reviewer, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewerList[position])

        val removeBtn: Button = holder.itemView.findViewById(R.id.button_remove_reviewer)

        if (HCGlobal.getInstance().currentActivity is JobSettingActivity) {
            if (! (HCGlobal.getInstance().currentActivity as JobSettingActivity).getViewModel().isUserManager) {
                removeBtn.visibility = View.GONE
            }
        }

        removeBtn.setOnClickListener {
            if (HCGlobal.getInstance().currentActivity is ProcessSettingActivity) {
                (HCGlobal.getInstance().currentActivity as ProcessSettingActivity).removeRoleFromProcessSetting(reviewerList[position])
                reviewerList.removeAt(position)
                notifyDataSetChanged()
            } else {
                val intent = Intent(HCGlobal.getInstance().currentActivity, RemoveUserRoleActivity::class.java)
                intent.putExtra("clientId", reviewerList[position].clientId)
                intent.putExtra("reviewerId", reviewerList[position].id)
                intent.putExtra("clientName", reviewerList[position].fullName)
                intent.putExtra("reviewType", reviewerList[position].reviewerType)
                intent.putExtra("jobId", jobProcessId)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
                (HCGlobal.getInstance().currentActivity as JobSettingActivity).overridePendingVTransitionEnter()
                (HCGlobal.getInstance().currentActivity as JobSettingActivity).finish()
            }
        }
    }

    override fun getItemCount(): Int {
        return if(::reviewerList.isInitialized) reviewerList.size else 0
    }

    fun updateReviewerList(reviewerList: ArrayList<ReviewerEntity>, jobProcessId: Int){
        this.reviewerList = reviewerList
        this.jobProcessId = jobProcessId
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ReviewerItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = ReviewerViewVM()

        fun bind(reviewer: ReviewerEntity){
            viewModel.bind(reviewer)
            binding.viewModel = viewModel
        }
    }
}