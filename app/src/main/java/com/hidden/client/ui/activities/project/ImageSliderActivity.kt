package com.hidden.client.ui.activities.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.datamodels.HCCandidateProjectAssets
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.ProjectSlidingImageAdapter

class ImageSliderActivity : BaseActivity() {

    private var imgSourceList: List<HCCandidateProjectAssets> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)

        imgSourceList =
            HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex].candidate__projects[HCGlobal.getInstance().currentProjectIndex].candidate__project_assets

        init()
    }

    private fun init() {

        var mPager = findViewById(R.id.container) as ViewPager
        mPager!!.adapter = ProjectSlidingImageAdapter(this, this.imgSourceList)

    }
}
