package com.hidden.client.ui.activities.settings

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.SlidingImageAdapter
import com.hidden.client.ui.adapters.SlidingProjectImageAdapter

class JobImageSliderActivity : BaseActivity() {

    private var imgSourceList: ArrayList<HCJobDetailTile> = arrayListOf()
    private var projectDetailImgList: Array<String>? = null
    private var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_image_slider)

        // projectDetailActivity image
        projectDetailImgList = intent.getStringArrayExtra("projectDetailImgList")
        type = intent.getStringExtra("type").safeValue()

        if (projectDetailImgList == null) {
            for (item in HCGlobal.getInstance().currentJobTitleList) {
                if (item.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.TEXT.value)) continue
                imgSourceList.add(item.getJobDetailTile())
            }

            init()
        } else {
            projectDetailImg()
        }
    }

    private fun init() {
        val mPager = findViewById<ViewPager>(R.id.container)
        mPager.adapter = SlidingImageAdapter(this, this.imgSourceList)
    }

    private fun projectDetailImg() {
        val mPager = findViewById<ViewPager>(R.id.container)
        mPager.adapter = SlidingProjectImageAdapter(this, this.projectDetailImgList!!, type)
    }
}
