package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.hidden.client.databinding.ReviewerItemBinding
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.viewmodels.main.ReviewerViewVM
import com.daimajia.swipe.SimpleSwipeListener
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ProcessSettingActivity

class ReviewerListAdapter: RecyclerView.Adapter<ReviewerListAdapter.ViewHolder>() {

    private lateinit var reviewerList: ArrayList<ReviewerEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ReviewerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_reviewer, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewerList[position])

        if (HCGlobal.getInstance().currentActivity !is ProcessSettingActivity) {
            val removeBtn: Button = holder.itemView.findViewById(R.id.button_remove_reviewer)
            removeBtn.visibility = View.GONE
        }
        val swipeLayout: SwipeLayout = holder.itemView.findViewById(R.id.swipe)
        swipeLayout.addSwipeListener(object : SimpleSwipeListener() {

            override fun onOpen(layout: SwipeLayout) {
                if (HCGlobal.getInstance().currentActivity is ProcessSettingActivity) {
                    (HCGlobal.getInstance().currentActivity as ProcessSettingActivity).removeRoleFromProcessSetting(reviewerList[position])

                    reviewerList.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return if(::reviewerList.isInitialized) reviewerList.size else 0
    }

    fun updateReviewerList(reviewerList: ArrayList<ReviewerEntity>){
        this.reviewerList = reviewerList
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