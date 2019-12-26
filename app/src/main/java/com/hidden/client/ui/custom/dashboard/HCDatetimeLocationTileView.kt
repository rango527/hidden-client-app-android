package com.hidden.client.ui.custom.dashboard

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R
import com.hidden.client.models.DashboardTileEntity

class HCDatetimeLocationTileView : LinearLayout {

    constructor(context: Context, data: DashboardTileEntity? = null) : super(context) {

        if (data != null) {

            if (data.getTileContentList().isEmpty()) {
                inflate(context, R.layout.dashboard_datetime_location_tile_view, this)

                val txtHeaderTitle: TextView = findViewById(R.id.text_header_title);
                txtHeaderTitle.setText(data.title)

                val textEmptyStatus: TextView = findViewById(R.id.text_empty_status)
                textEmptyStatus.text = data.emptyStatus

                val imgStatusIcon: ImageView = findViewById(R.id.image_empty_status)
                imgStatusIcon.setImageResource(R.drawable.empty_upcoming_interviews)
            } else {

            }
        }
    }

}