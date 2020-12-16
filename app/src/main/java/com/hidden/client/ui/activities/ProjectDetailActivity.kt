package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ProjectEntity
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.settings.JobImageSliderActivity
import com.hidden.client.ui.adapters.HCProjectImageAdapter

class ProjectDetailActivity : BaseActivity() {

    private lateinit var project: ProjectEntity

    private lateinit var txtProject: TextView
    private lateinit var txtCompany: TextView
    private lateinit var txtBrief: TextView
    private lateinit var txtWhatDid: TextView

    private lateinit var imgMain: ImageView

    private var imageList: ArrayList<String> = arrayListOf()
    private var videoList: ArrayList<String> = arrayListOf()

    private lateinit var rvImages: RecyclerView
    private lateinit var rvVideos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        initCloseButton()

        project = HCGlobal.getInstance().shortlistCandidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().getProjectList()[HCGlobal.getInstance().currentProjectIndex]

        txtProject = findViewById(R.id.text_project)
        txtCompany = findViewById(R.id.text_company)
        txtBrief = findViewById(R.id.text_brief)
        txtWhatDid = findViewById(R.id.text_what_did)

        imgMain = findViewById(R.id.img_main)

        txtProject.text = project.title
        txtCompany.text = project.name
        txtBrief.text = project.brief
        txtWhatDid.text = project.activity

        var mainImgUrl = ""

        for (assets in project.getAssetsList()) {
            if (assets.mainImage) {
                mainImgUrl = assets.url
            }
            if (assets.type == Enums.ProjectAssetsType.IMAGE.value) {
                imageList.add(assets.url)
            }
            if (assets.type == Enums.ProjectAssetsType.VIDEO.value) {
                videoList.add(assets.url.substring(0, assets.url.length - 3) + "jpg")
            }
        }

        Glide.with(this).load(mainImgUrl).into(imgMain)

        rvImages = findViewById(R.id.recyclerview_image)
        val layoutImage: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvImages.layoutManager = layoutImage
        rvImages.setHasFixedSize(true)
        val imageAdapter = HCProjectImageAdapter(imageList.toTypedArray(),this)
        rvImages.adapter = imageAdapter

//        rvImages.setOnClickListener {
//            val intent = Intent(HCGlobal.getInstance().currentActivity, JobImageSliderActivity::class.java)
//            HCGlobal.getInstance().currentActivity.startActivity(intent)
//        }

        rvVideos = findViewById(R.id.recyclerview_video)
        val layoutVideo: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvVideos.layoutManager = layoutVideo
        rvVideos.setHasFixedSize(true)
        val videoAdapter = HCProjectImageAdapter(videoList.toTypedArray(),this)
        rvVideos.adapter = videoAdapter
    }
}
