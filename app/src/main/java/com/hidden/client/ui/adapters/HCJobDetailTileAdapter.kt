package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCJobDetailTileBinding
import com.hidden.client.databinding.HCMetricBinding
import com.hidden.client.enums.ColorSchema
import com.hidden.client.enums.JobDetailTileType
import com.hidden.client.ui.viewholders.HCJobDetailTileVH
import com.hidden.client.ui.viewholders.HCMetricVH
import com.hidden.client.ui.viewmodels.HCJobDetailTileViewModel
import com.hidden.client.ui.viewmodels.HCMetricViewModel
import org.w3c.dom.Text

class HCJobDetailTileAdapter(
    private val context:Context,
    private val list: ArrayList<HCJobDetailTileViewModel>) : RecyclerView.Adapter<HCJobDetailTileVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCJobDetailTileVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val jobDetailTileBinding: HCJobDetailTileBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_job_detail_tile, parent, false)

        return HCJobDetailTileVH(jobDetailTileBinding)
    }

    override fun onBindViewHolder(holder: HCJobDetailTileVH, position: Int) {

        val jobDetailTileViewModel = list[position]
        holder.bind(jobDetailTileViewModel)

        val layoutText: LinearLayout = holder.itemView.findViewById(R.id.layout_text_tile);
        val layoutImage: LinearLayout = holder.itemView.findViewById(R.id.layout_img_title);

        if (jobDetailTileViewModel.getJobDetailTile().getJobDetailTitleType() ===  JobDetailTileType.TEXT.value) {
            layoutImage.visibility = View.GONE
        } else if (jobDetailTileViewModel.getJobDetailTile().getJobDetailTitleType() ===  JobDetailTileType.IMAGE.value) {
            layoutText.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}