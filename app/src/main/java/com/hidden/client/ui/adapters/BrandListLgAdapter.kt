package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.CandidateBrandLgItemBinding
import com.hidden.client.models.entity.BrandEntity
import com.hidden.client.ui.viewmodels.main.BrandViewVM

class BrandListLgAdapter: RecyclerView.Adapter<BrandListLgAdapter.ViewHolder>() {

    private lateinit var brandList: List<BrandEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CandidateBrandLgItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_candidate_lg_brand, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(brandList[position])
    }

    override fun getItemCount(): Int {
        return if(::brandList.isInitialized) brandList.size else 0
    }

    fun updateBrandList(brandList: List<BrandEntity>){
        this.brandList = brandList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CandidateBrandLgItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = BrandViewVM()

        fun bind(brand: BrandEntity){
            viewModel.bind(brand)
            binding.viewModel = viewModel
        }
    }
}