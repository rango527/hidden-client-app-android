package com.hidden.client.ui.activities.settings

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.SlidingImageAdapter

class JobImageSliderActivity : BaseActivity() {

    private var imgSourceList: ArrayList<HCJobDetailTile> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_image_slider)

        for (item in HCGlobal.getInstance().currentJobTitleList) {
            if (item.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.TEXT.value)) continue
            imgSourceList.add(item.getJobDetailTile())
        }

        init()
    }

    private fun init() {

        val mPager = findViewById<ViewPager>(R.id.container)
        mPager.adapter = SlidingImageAdapter(this, this.imgSourceList)

    }
}
