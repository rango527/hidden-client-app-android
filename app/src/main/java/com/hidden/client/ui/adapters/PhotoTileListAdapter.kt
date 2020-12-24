package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.DashboardPhotoTileViewBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HCJobActivity
import com.hidden.client.ui.viewmodels.custom.DashboardPhotoTileViewVM

class PhotoTileListAdapter(
    private val context:Context,
    private val list: ArrayList<DashboardPhotoTileViewVM>) : RecyclerView.Adapter<PhotoTileListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DashboardPhotoTileViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_photo_tile, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val viewModel: DashboardPhotoTileViewVM = list[position]
        holder.bind(viewModel)

        val layoutLive: LinearLayout = holder.itemView.findViewById(R.id.layout_live)

        when (viewModel.getTileContent().tag) {
            "LIVE" -> {
                layoutLive.setBackgroundResource(R.drawable.button_round_green_12)
            }
            "ARCHIVED" -> {
                layoutLive.setBackgroundResource(R.drawable.button_round_red_24)
            }
            "DRAFT" -> {
                layoutLive.setBackgroundResource(R.drawable.button_round_gray_12)
            }
            else -> {
                layoutLive.setBackgroundResource(R.drawable.button_round_gray_12)
            }
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(context, HCJobActivity::class.java)
            intent.putExtra("jobId", viewModel.getTileContent().jobId.toString())
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: DashboardPhotoTileViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind (viewModel: DashboardPhotoTileViewVM) {
            this.binding.viewModel = viewModel
            binding.executePendingBindings()
        }

    }
}