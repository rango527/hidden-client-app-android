package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.settings.JobImageSliderActivity
import com.hidden.client.ui.viewholders.HCImageVH
import com.rishabhharit.roundedimageview.RoundedImageView

class HCProjectImageAdapter(var list: Array<String>, var type: String, var context: Context) :
    RecyclerView.Adapter<HCImageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCImageVH {

        val rv: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row_hightlight_image, parent, false)


        return HCImageVH(rv)
    }

    override fun onBindViewHolder(holder: HCImageVH, position: Int) {

        val layoutImage: RoundedImageView = holder.itemView.findViewById(R.id.img)

        val imgUrl: String = list[position]
        Glide.with(context).load(imgUrl).into(holder.img)

        layoutImage.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobImageSliderActivity::class.java)
            intent.putExtra("projectDetailImgList", list)
            intent.putExtra("type", type)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}