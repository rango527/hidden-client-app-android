package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateItemBinding
import com.hidden.client.databinding.ShortlistItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.ShortlistEntity
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.models.json.CandidateJson
import com.hidden.client.ui.activities.settings.CandidateDetailActivity
import com.hidden.client.ui.activities.settings.CandidateListActivity
import com.hidden.client.ui.viewmodels.main.CandidateViewVM
import com.hidden.client.ui.viewmodels.main.ShortlistListVM
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM

class ShortlistListAdapter: RecyclerView.Adapter<ShortlistListAdapter.ViewHolder>() {

    private lateinit var shortlistList: List<ShortlistEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortlistListAdapter.ViewHolder {
        val binding: ShortlistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.viewpager_profile_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortlistListAdapter.ViewHolder, position: Int) {
        holder.bind(shortlistList[position])

        holder.itemView.setOnClickListener(View.OnClickListener {

//            var candidate = shortlistList[position]
        })
    }

    override fun getItemCount(): Int {
        return if(::shortlistList.isInitialized) shortlistList.size else 0
    }

    fun updateCandidateList(shortlistList: List<ShortlistEntity>){
        this.shortlistList = shortlistList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ShortlistItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = ShortlistViewVM()

        fun bind(shortlist: ShortlistEntity){
            viewModel.bind(shortlist)
            binding.viewModel = viewModel
        }
    }
}