package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.ReviewerItemBinding
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.viewmodels.main.ReviewerViewVM

class ReviewerListAdapter: RecyclerView.Adapter<ReviewerListAdapter.ViewHolder>() {

    private lateinit var reviewerList: List<ReviewerEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ReviewerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_reviewer, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewerList[position])
    }

    override fun getItemCount(): Int {
        return if(::reviewerList.isInitialized) reviewerList.size else 0
    }

    fun updateReviewerList(reviewerList: List<ReviewerEntity>){
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