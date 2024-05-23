package com.hidden.client.ui.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R

class HCImageVH(rv: View) : RecyclerView.ViewHolder(rv) {

    var img: ImageView = rv.findViewById(R.id.img) as ImageView

}