package com.hidden.client.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R

class HCYourJobVH : RecyclerView.ViewHolder {

    var imgJob: ImageView
    var textJobTitle: TextView
    var textJobLocation: TextView

    constructor(rv: View) : super(rv) {
        imgJob = rv.findViewById(R.id.img_job) as ImageView
        textJobTitle = rv.findViewById(R.id.text_job_title) as TextView
        textJobLocation = rv.findViewById(R.id.text_job_location) as TextView
    }

}