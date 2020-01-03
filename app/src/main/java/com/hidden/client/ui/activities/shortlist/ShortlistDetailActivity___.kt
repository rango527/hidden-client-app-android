//package com.hidden.client.ui.activities.shortlist
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.google.android.flexbox.FlexboxLayout
//import com.hidden.client.R
//import com.hidden.client.helpers.HCGlobal
//import com.hidden.client.helpers.extension.safeValue
//import com.hidden.client.models_.HCBrand
//import com.hidden.client.models_.HCProject
//import com.hidden.client.models_.HCWorkExperience
//import com.hidden.client.ui.BaseActivity
//import com.hidden.client.ui.activities.HomeActivity
//import com.hidden.client.ui.adapters.HCBrandAdapter
//import com.hidden.client.ui.adapters.HCProjectAdapter
//import com.hidden.client.ui.adapters.HCWorkExperienceAdapter
//import com.hidden.client.ui.custom.SkillItemView
//import com.hidden.client.ui.viewmodels___.HCBrandViewModel
//import com.hidden.client.ui.viewmodels___.HCProjectViewModel
//import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
//import de.hdodenhof.circleimageview.CircleImageView
//import kotlinx.android.synthetic.main.activity_shortlist_detail.*
//
//class ShortlistDetailActivity___ : BaseActivity(), View.OnClickListener {
//    private lateinit var imgPhoto: CircleImageView
//    private lateinit var textName: TextView
//    private lateinit var textLocation: TextView
//    private lateinit var textJobTitle: TextView
//    private lateinit var textSnapshot: TextView
//    private lateinit var layoutSkill: FlexboxLayout
//
//    // RecyclerView for Brand
//    private lateinit var rvBrand: RecyclerView
//    private lateinit var brandViewModel: HCBrandViewModel
//    private lateinit var brandAdapter: HCBrandAdapter
//    private lateinit var layoutBrandManager: RecyclerView.LayoutManager
//
//    // RecyclerView for Brand
//    private lateinit var rvProject: RecyclerView
//    private lateinit var projectViewModel: HCProjectViewModel
//    private lateinit var projectAdapter: HCProjectAdapter
//    private lateinit var layoutProjectdManager: RecyclerView.LayoutManager
//
//    // RecyclerView for Work Experience
//    private lateinit var rvWorkExperience: RecyclerView
//    private lateinit var workExperienceViewModel: HCWorkExperienceViewModel
//    private var workExperienceList: MutableList<HCWorkExperience> = mutableListOf()
//    private lateinit var workExperienceAdapter: HCWorkExperienceAdapter
//
//    // Approve & Reject
//    private lateinit var layoutApprove: LinearLayout
//    private lateinit var layoutReject: LinearLayout
//
//    // Labels
//    private lateinit var lblSnapshot: TextView
//    private lateinit var lblBrand: TextView
//    private lateinit var lblProject: TextView
//    private lateinit var lblSkills: TextView
//    private lateinit var lblExperience: TextView
//
//    // Background
//    private lateinit var layoutBackground: ConstraintLayout
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_shortlist_detail)
//
//        HCGlobal.getInstance().currentActivity = this
//
//        initCloseButton()
//
//        imgPhoto = findViewById(R.id.image_photo)
//        textName = findViewById(R.id.text_name)
//        textLocation = findViewById(R.id.text_location)
//        textJobTitle = findViewById(R.id.text_job_title)
//        textSnapshot = findViewById(R.id.text_snapshot)
//
//        layoutApprove = findViewById(R.id.layout_approve)
//        layoutApprove.setOnClickListener(this)
//        layoutReject = findViewById(R.id.layout_reject)
//        layoutReject.setOnClickListener(this)
//
//        lblSnapshot = findViewById(R.id.lbl_snapshot)
//        lblBrand = findViewById(R.id.lbl_brand)
//        lblProject = findViewById(R.id.lbl_project)
//        lblSkills = findViewById(R.id.lbl_skills)
//        lblExperience = findViewById(R.id.lbl_experience)
//
//        layoutBackground = findViewById(R.id.layout_background)
//
//        // Brand RecyclerView
//        rvBrand = findViewById(R.id.recyclerview_brand)
//        brandViewModel = ViewModelProviders.of(this).get(HCBrandViewModel::class.java)
//        brandViewModel.getBrandList().observe(this, Observer { brandViewModel ->
//            brandAdapter = HCBrandAdapter(this, brandViewModel)
//
//            layoutBrandManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            rvBrand.layoutManager = layoutBrandManager
//            rvBrand.setHasFixedSize(true)
//
//            rvBrand.adapter = brandAdapter
//
//            if (brandViewModel.isEmpty()) {
//                lblBrand.visibility = View.GONE
//            }
//        })
//
//        // Project RecyclerView
//        rvProject = findViewById(R.id.recyclerview_project)
//        projectViewModel = ViewModelProviders.of(this).get(HCProjectViewModel::class.java)
//        projectViewModel.getProjectList().observe(this, Observer { projectViewModel ->
//            projectAdapter = HCProjectAdapter(this, projectViewModel)
//
//            layoutProjectdManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            rvProject.layoutManager = layoutProjectdManager
//            rvProject.setHasFixedSize(true)
//
//            rvProject.adapter = projectAdapter
//
//            if (projectViewModel.isEmpty()) {
//                lblProject.visibility = View.GONE
//            }
//        })
//
//        // Work Experience RecyclerView
//        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)
//        workExperienceViewModel =
//            ViewModelProviders.of(this).get(HCWorkExperienceViewModel::class.java)
//        workExperienceViewModel.getWorkExperienceList()
//            .observe(this, Observer { workExperienceViewModels ->
//                workExperienceAdapter = HCWorkExperienceAdapter(this, workExperienceViewModels)
//
//                rvWorkExperience.layoutManager = LinearLayoutManager(this)
//                rvWorkExperience.setHasFixedSize(true)
//
//                rvWorkExperience.adapter = workExperienceAdapter
//
//                if (workExperienceViewModels.isEmpty()) {
//                    lblExperience.visibility = View.GONE
//                }
//            })
//
//        // -------------------------------------------------------------------
//
//        var candidateDetail =
//            HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex]
//
//        layoutBackground.setBackgroundResource(
//            resources.getIdentifier(candidateDetail.avatar__colour, "drawable", packageName)
//        )
//
//        val photoUrl = candidateDetail.avatar__image
//        Glide.with(this).load(photoUrl).into(imgPhoto)
//
//        textName.setText(candidateDetail.avatar__name)
//        textLocation.setText(candidateDetail.candidate_city__name)
//        textJobTitle.setText(candidateDetail.job_title_1__name + " | " + candidateDetail.job_title_2__name + " | " + candidateDetail.job_title_3__name)
//        textSnapshot.setText(candidateDetail.candidate__hidden_says)
//
//        if (candidateDetail.candidate__hidden_says.safeValue().isEmpty()) {
//            lblSnapshot.visibility = View.GONE
//        }
//
//        // Set Brand List
//        var candidateBrandList: ArrayList<HCBrand> = arrayListOf()
//        for (candidate_brand_response in candidateDetail.candidate__brands) {
//            candidateBrandList.add(HCBrand(candidate_brand_response.asset__cloudinary_url))
//        }
//        brandViewModel.setBrandList(candidateBrandList)
//
//        // Set ProjectList
//        var candidateProjectList: ArrayList<HCProject> = arrayListOf()
//        for (candidate_project_response in candidateDetail.candidate__projects) {
//            var mainImageUrl = ""
//
//            val mainAssets =
//                candidate_project_response.candidate__project_assets.filter { it.project_asset__is_main_image }
//            if (mainAssets.isNotEmpty()) {
//                mainImageUrl = mainAssets[0].project_asset__cloudinary_url
//            }
//
//            if (mainImageUrl.isNotBlank()) {
//                val project = HCProject(
//                    candidate_project_response.project__project_id,
//                    candidate_project_response.project__title,
//                    candidate_project_response.project__brief,
//                    candidate_project_response.project__activity,
//                    candidate_project_response.brand__name,
//                    candidate_project_response.brand_logo__cloudinary_url,
//                    mainImageUrl
//                )
//                candidateProjectList.add(project)
//            }
//        }
//        projectViewModel.setProjectList(candidateProjectList)
//
//        // Skill Layout
//        layoutSkill = findViewById(R.id.layout_skills)
//
//        for (candidate_skill in candidateDetail.candidate__skills) {
//            var skillItemView = SkillItemView(
//                this,
//                candidate_skill.skill__name,
//                candidate_skill.candidate_skill__ranking
//            )
//            layoutSkill.addView(skillItemView)
//        }
//
//        if (candidateDetail.candidate__skills.isEmpty()) {
//            lbl_skills.visibility = View.GONE
//        }
//
//        // Set WorkExperienceList
//        var workExperienceList: ArrayList<HCWorkExperience> = arrayListOf()
//        for (work_experience_response in candidateDetail.candidate__work_experiences) {
//
//            var workExperience = HCWorkExperience()
//            workExperience.setExperienceId(work_experience_response.work_experience__work_experience_id)
//            workExperience.setExperienceJobTitle(work_experience_response.work_experience__job_title)
//            workExperience.setExperienceDescription(work_experience_response.work_experience__description)
//            workExperience.setExperienceFrom(work_experience_response.work_experience__worked_from)
//            workExperience.setExperienceTo(work_experience_response.work_experience__worked_to)
//            workExperience.setBrandName(work_experience_response.brand__name)
//            workExperience.setBrandLogoUrl(work_experience_response.asset__cloudinary_url)
//
//            workExperienceList.add(workExperience)
//        }
//        workExperienceViewModel.setWorkExperienceList(workExperienceList)
//    }
//
//    override fun onClick(v: View?) {
//        when (v!!.id) {
//            R.id.layout_approve -> {
//                val intent = Intent(this, ApproveActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.layout_reject -> {
//                val intent = Intent(this, RejectActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
//}
