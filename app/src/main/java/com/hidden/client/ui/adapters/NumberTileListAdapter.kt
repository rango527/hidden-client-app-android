package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.DashboardNumberTileViewBinding
import com.hidden.client.ui.viewmodels.custom.DashboardNumberTileViewVM

class NumberTileListAdapter(
    private val list: ArrayList<DashboardNumberTileViewVM>) : RecyclerView.Adapter<NumberTileListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DashboardNumberTileViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_number_tile, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewModel: DashboardNumberTileViewVM = list[position]
        holder.bind(viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: DashboardNumberTileViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (viewModel: DashboardNumberTileViewVM) {
            this.binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}