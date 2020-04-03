package com.hidden.client.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCJobDetailTileBinding
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.settings.JobImageSliderActivity
import com.hidden.client.ui.viewholders.HCJobDetailTileVH
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel

class HCJobDetailTileAdapter(
    private val list: ArrayList<HCJobDetailTileViewModel>) : RecyclerView.Adapter<HCJobDetailTileVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCJobDetailTileVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val jobDetailTileBinding: HCJobDetailTileBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_job_detail_tile, parent, false)

        HCGlobal.getInstance().currrentJobTitleList = list

        return HCJobDetailTileVH(jobDetailTileBinding)
    }

    override fun onBindViewHolder(holder: HCJobDetailTileVH, position: Int) {

        val jobDetailTileViewModel = list[position]
        holder.bind(jobDetailTileViewModel)

        val layoutText: LinearLayout = holder.itemView.findViewById(R.id.layout_text_tile)
        val layoutImage: LinearLayout = holder.itemView.findViewById(R.id.layout_img_title)

        when {
            jobDetailTileViewModel.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.TEXT.value) -> {
                layoutText.visibility = View.VISIBLE
                layoutImage.visibility = View.GONE
            }
            jobDetailTileViewModel.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.IMAGE.value) -> {
                layoutText.visibility = View.GONE
                layoutImage.visibility = View.VISIBLE
            }
            jobDetailTileViewModel.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.VIDEO.value) -> {
                layoutText.visibility = View.GONE
                layoutImage.visibility = View.VISIBLE
            }
            else -> {
                layoutText.visibility = View.GONE
                layoutImage.visibility = View.GONE
            }
        }

        layoutImage.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobImageSliderActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}