package com.hidden.client.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.settings.JobImageSliderActivity
import com.hidden.client.ui.activities.settings.VideoPlayerActivity

class SlidingProjectImageAdapter(context: Context, private val dataList: Array<String>, private var type: String) : PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.image_slider_item, view, false)!!

        val imgPhoto = imageLayout
            .findViewById(R.id.image_photo) as ImageView

        val imgBackground: ImageView = imageLayout.findViewById(R.id.image_background)

        val txtClose: TextView = imageLayout.findViewById(R.id.text_close)
        txtClose.setOnClickListener {
            HCGlobal.getInstance().currentActivity.finish()
        }

        val txtShow: TextView = imageLayout.findViewById(R.id.text_show_more)
        val txtTitle: TextView = imageLayout.findViewById(R.id.text_title)
        txtTitle.visibility = View.GONE
        txtShow.visibility = View.GONE

        val txtPageNum: TextView = imageLayout.findViewById(R.id.text_page)
        txtPageNum.text = (position + 1).toString() + "/" + dataList.size.toString()

        Glide.with(HCGlobal.getInstance().currentActivity).load(dataList[position]).into(imgPhoto)
        Glide.with(HCGlobal.getInstance().currentActivity).load(dataList[position]).into(imgBackground)

        imgPhoto.setOnClickListener {
            if (type == Enums.ProjectAssetsType.IMAGE.value) {
                txtTitle.visibility = View.VISIBLE
                txtShow.text = "... Show more"
            } else if (type == Enums.ProjectAssetsType.VIDEO.value) {
                val intent = Intent(HCGlobal.getInstance().currentActivity, VideoPlayerActivity::class.java)
                intent.putExtra("movieUrl", dataList[position].substring(0, dataList[position].length - 3) + "mp4")
                HCGlobal.getInstance().currentActivity.startActivity(intent)
                (HCGlobal.getInstance().currentActivity as JobImageSliderActivity).overridePendingVTransitionEnter()
            }
        }

        if (type == Enums.ProjectAssetsType.VIDEO.value) {
            val imgPlay: ImageView = imageLayout.findViewById(R.id.image_play)
            imgPlay.visibility = View.VISIBLE
        }

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}