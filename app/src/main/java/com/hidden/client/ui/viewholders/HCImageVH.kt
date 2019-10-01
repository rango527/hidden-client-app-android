package com.hidden.client.ui.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R

class HCImageVH : RecyclerView.ViewHolder {

    var img: ImageView

    constructor(rv: View) : super(rv) {
        img = rv.findViewById(R.id.img) as ImageView
    }
}