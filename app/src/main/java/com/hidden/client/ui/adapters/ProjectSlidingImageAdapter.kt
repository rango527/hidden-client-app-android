package com.hidden.client.ui.adapters

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
import com.hidden.client.datamodels.HCCandidateProjectAssets
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.project.ImageSliderActivity
import com.hidden.client.ui.activities.settings.VideoPlayerActivity

class ProjectSlidingImageAdapter(context: Context, private val dataList: List<HCCandidateProjectAssets>) : PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.project_image_slider_item, view, false)!!

        val imgPhoto = imageLayout
            .findViewById(R.id.image_photo) as ImageView

        val imgBackground: ImageView = imageLayout.findViewById(R.id.image_background)

        val txtClose: TextView = imageLayout.findViewById(R.id.text_close)
        txtClose.setOnClickListener {
            HCGlobal.getInstance().currentActivity.finish()
        }

        if (dataList[position].project_asset__asset_type == DetailTileType.IMAGE.value) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(dataList[position].project_asset__cloudinary_url).into(imgPhoto)
            Glide.with(HCGlobal.getInstance().currentActivity).load(dataList[position].project_asset__cloudinary_url).into(imgBackground)
        } else {
            val movieUrl = dataList[position].project_asset__cloudinary_url.substring(0, dataList[position].project_asset__cloudinary_url.length - 3) + "jpg"
            Glide.with(HCGlobal.getInstance().currentActivity).load(movieUrl).into(imgPhoto)
            Glide.with(HCGlobal.getInstance().currentActivity).load(movieUrl).into(imgBackground)
        }

        imgPhoto.setOnClickListener {
            if (dataList[position].project_asset__asset_type == DetailTileType.VIDEO.value) {
                val intent = Intent(HCGlobal.getInstance().currentActivity, VideoPlayerActivity::class.java)
                intent.putExtra("movieUrl", dataList[position].project_asset__cloudinary_url)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
                (HCGlobal.getInstance().currentActivity as ImageSliderActivity).overridePendingVTransitionEnter()
            }
        }

        if (dataList[position].project_asset__asset_type == DetailTileType.VIDEO.value) {
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