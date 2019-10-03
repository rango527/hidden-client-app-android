package com.hidden.client.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R

class HCProcessVH : RecyclerView.ViewHolder {

    var imgPhoto: ImageView
    var textName: TextView
    var textFor: TextView

    constructor(rv: View) : super(rv) {
        imgPhoto = rv.findViewById(R.id.img_photo) as ImageView
        textName = rv.findViewById(R.id.text_name)
        textFor = rv.findViewById(R.id.text_for)
    }
}