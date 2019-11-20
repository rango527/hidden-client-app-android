package com.hidden.client.ui.activities.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.SlidingImageAdapter
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.collections.ArrayList

class JobImageSliderActivity : BaseActivity() {

    private var imgSourceList: ArrayList<HCJobDetailTile> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_image_slider)

        for (item in HCGlobal.getInstance().currrentJobTitleList) {
            if (item.getJobDetailTile().getJobDetailTileType().equals(DetailTileType.TEXT.value)) continue
            imgSourceList.add(item.getJobDetailTile())
        }

        init()
    }

    private fun init() {

        var mPager = findViewById(R.id.container) as ViewPager
        mPager!!.adapter = SlidingImageAdapter(this, this.imgSourceList)

    }
}
