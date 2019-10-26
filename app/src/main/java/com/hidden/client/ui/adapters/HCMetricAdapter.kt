package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCMetricBinding
import com.hidden.client.enums.ColorSchema
import com.hidden.client.ui.viewholders.HCMetricVH
import com.hidden.client.ui.viewmodels.HCMetricViewModel
import org.w3c.dom.Text

class HCMetricAdapter(
    private val context:Context,
    private val list: ArrayList<HCMetricViewModel>) : RecyclerView.Adapter<HCMetricVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCMetricVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val metricBinding: HCMetricBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_metric, parent, false)

        return HCMetricVH(metricBinding)
    }

    override fun onBindViewHolder(holder: HCMetricVH, position: Int) {

        val metricModel = list[position]
        holder.bind(metricModel)

        if (metricModel.getColorSchema() == ColorSchema.DARK.value) {
            val layoutBackground: LinearLayout = holder.itemView.findViewById(R.id.layout_background)
            val text_title: TextView = holder.itemView.findViewById(R.id.text_title)
            val text_value: TextView = holder.itemView.findViewById(R.id.text_value)

            layoutBackground.setBackgroundResource(R.drawable.panel_round_shadow_black)
            text_title.setTextColor(ContextCompat.getColor(context, R.color.colorWhite_1))
            text_value.setTextColor(ContextCompat.getColor(context, R.color.colorWhite_1))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}