package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateBrandItemBinding
import com.hidden.client.models.BrandEntity
import com.hidden.client.ui.viewmodels.main.BrandViewVM

class BrandListAdapter: RecyclerView.Adapter<BrandListAdapter.ViewHolder>() {

    private lateinit var brandList: List<BrandEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandListAdapter.ViewHolder {
        val binding: CandidateBrandItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_candidate_brand, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandListAdapter.ViewHolder, position: Int) {
        holder.bind(brandList[position])
    }

    override fun getItemCount(): Int {
        return if(::brandList.isInitialized) brandList.size else 0
    }

    fun updateBrandList(brandList: List<BrandEntity>){
        this.brandList = brandList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateBrandItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = BrandViewVM()

        fun bind(brand: BrandEntity){
            viewModel.bind(brand)
            binding.viewModel = viewModel
        }
    }
}