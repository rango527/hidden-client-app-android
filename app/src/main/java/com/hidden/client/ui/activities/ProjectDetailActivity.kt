package com.hidden.client.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.datamodels.HCCandidateProjectResponse
import com.hidden.client.enums.DetailTileType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.HCProjectImageAdapter

class ProjectDetailActivity : BaseActivity() {

    private lateinit var project: HCCandidateProjectResponse

    private lateinit var txtProject: TextView
    private lateinit var txtCompany: TextView
    private lateinit var txtBrief: TextView
    private lateinit var txtWhatdid: TextView

    private lateinit var imgMain: ImageView

    private var imageList: ArrayList<String> = arrayListOf()
    private var videoList: ArrayList<String> = arrayListOf()

    private lateinit var rvImages: RecyclerView
    private lateinit var rvVideos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        initCloseButton()

        project =
            HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex].candidate__projects[HCGlobal.getInstance().currentProjectIndex]

        txtProject = findViewById(R.id.text_project)
        txtCompany = findViewById(R.id.text_company)
        txtBrief = findViewById(R.id.text_brief)
        txtWhatdid = findViewById(R.id.text_what_did)

        imgMain = findViewById(R.id.img_main)

        txtProject.text = project.project__title
        txtCompany.text = project.brand__name
        txtBrief.text = project.project__brief
        txtWhatdid.text = project.project__activity

        var mainImgUrl = ""

        for (assets in project.candidate__project_assets) {
            if (assets.project_asset__is_main_image) {
                mainImgUrl = assets.project_asset__cloudinary_url
            }
            if (assets.project_asset__asset_type == DetailTileType.IMAGE.value) {
                imageList.add(assets.project_asset__cloudinary_url)
            }
            if (assets.project_asset__asset_type == DetailTileType.VIDEO.value) {
                videoList.add(assets.project_asset__cloudinary_url.substring(0, assets.project_asset__cloudinary_url.length - 3) + "jpg")
            }
        }

        Glide.with(this).load(mainImgUrl).into(imgMain)

        rvImages = findViewById(R.id.recyclerview_image)
        val layoutImage: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvImages.layoutManager = layoutImage
        rvImages.setHasFixedSize(true)
        val imageAdapter = HCProjectImageAdapter(imageList.toTypedArray(),this)
        rvImages.adapter = imageAdapter

        rvVideos = findViewById(R.id.recyclerview_video)
        val layoutVideo: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvVideos.layoutManager = layoutVideo
        rvVideos.setHasFixedSize(true)
        val videoAdapter = HCProjectImageAdapter(videoList.toTypedArray(),this)
        rvVideos.adapter = videoAdapter
    }
}
