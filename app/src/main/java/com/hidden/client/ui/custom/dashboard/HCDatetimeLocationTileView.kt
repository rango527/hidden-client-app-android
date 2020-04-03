package com.hidden.client.ui.custom.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R
import com.hidden.client.models.entity.DashboardTileEntity

@SuppressLint("ViewConstructor")
class HCDatetimeLocationTileView(context: Context, data: DashboardTileEntity? = null) :
    LinearLayout(context) {

    init {
        if (data != null) {

            if (data.getTileContentList().isEmpty()) {
                inflate(context, R.layout.view_dashboard_datetime_location_tile, this)

                val txtHeaderTitle: TextView = findViewById(R.id.text_header_title)
                txtHeaderTitle.text = data.title

                val textEmptyStatus: TextView = findViewById(R.id.text_empty_status)
                textEmptyStatus.text = data.emptyStatus

                val imgStatusIcon: ImageView = findViewById(R.id.image_empty_status)
                imgStatusIcon.setImageResource(R.drawable.empty_upcoming_interviews)
            } else {

            }
        }
    }

}