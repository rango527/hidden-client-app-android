package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.adapters.HCBrandAdapter
import com.hidden.client.ui.adapters.HCWorkExperienceAdapter
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ShortlistDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imgPhoto: CircleImageView
    private lateinit var textName: TextView
    private lateinit var textLocation: TextView
    private lateinit var textJobTitle: TextView
    private lateinit var textSnapshot: TextView
    private lateinit var layoutSkill: FlexboxLayout

    // RecyclerView for Brand
    private lateinit var rvBrand: RecyclerView
    private lateinit var brandViewModel: HCBrandViewModel
    private lateinit var brandAdapter: HCBrandAdapter
    private lateinit var layoutBrandManager: RecyclerView.LayoutManager

    // RecyclerView for Work Experience
    private lateinit var rvWorkExperience: RecyclerView
    private lateinit var workExperienceViewModel: HCWorkExperienceViewModel
    private var workExperienceList: MutableList<HCWorkExperience> = mutableListOf()
    private lateinit var workExperienceAdapter: HCWorkExperienceAdapter

    // Approve & Reject
    private lateinit var layoutApprove: LinearLayout
    private lateinit var layoutReject: LinearLayout

    private lateinit var imgClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortlist_detail)

        HCGlobal.getInstance().currentActivity = this

        imgClose = findViewById(R.id.image_close);
        imgClose.setOnClickListener(this)

        imgPhoto = findViewById(R.id.image_photo)
        textName = findViewById(R.id.text_name)
        textLocation = findViewById(R.id.text_location)
        textJobTitle = findViewById(R.id.text_job_title)
        textSnapshot = findViewById(R.id.text_snapshot)

        layoutApprove = findViewById(R.id.layout_approve)
        layoutApprove.setOnClickListener(this)
        layoutReject = findViewById(R.id.layout_reject)
        layoutReject.setOnClickListener(this)

        // Brand RecyclerView
        rvBrand = findViewById(R.id.recyclerview_brand)
        brandViewModel = ViewModelProviders.of(this).get(HCBrandViewModel::class.java)
        brandViewModel.getBrandList().observe(this, Observer { brandViewModel ->
            brandAdapter = HCBrandAdapter(this, brandViewModel)

            layoutBrandManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvBrand.layoutManager = layoutBrandManager
            rvBrand.setHasFixedSize(true)

            rvBrand.adapter = brandAdapter
        })

        // Work Experience RecyclerView
        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)
        workExperienceViewModel =
            ViewModelProviders.of(this).get(HCWorkExperienceViewModel::class.java)
        workExperienceViewModel.getWorkExperienceList()
            .observe(this, Observer { workExperienceViewModels ->
                workExperienceAdapter = HCWorkExperienceAdapter(this, workExperienceViewModels)

                rvWorkExperience.layoutManager = LinearLayoutManager(this)
                rvWorkExperience.setHasFixedSize(true)

                rvWorkExperience.adapter = workExperienceAdapter
            })

        // -------------------------------------------------------------------

        var candidateDetail =
            HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex]

        val photoUrl = candidateDetail.avatar__image
        Glide.with(this).load(photoUrl).into(imgPhoto)

        textName.setText(candidateDetail.avatar__name)
        textLocation.setText(candidateDetail.candidate_city__name)
        textJobTitle.setText(candidateDetail.job_title_1__name + " | " + candidateDetail.job_title_2__name + " | " + candidateDetail.job_title_3__name)
        textSnapshot.setText(candidateDetail.candidate__hidden_says)

        // Set Brand List
        var candidateBrandList: ArrayList<HCBrand> = arrayListOf()
        for (candidate_brand_response in candidateDetail.candidate__brands) {
            candidateBrandList.add(HCBrand(candidate_brand_response.asset__cloudinary_url))
        }
        brandViewModel.setBrandList(candidateBrandList)

        // Skill Layout
        layoutSkill = findViewById(R.id.layout_skills)

        for (candidate_skill in candidateDetail.candidate__skills) {
            var skillItemView = SkillItemView(
                this,
                candidate_skill.skill__name,
                candidate_skill.candidate_skill__ranking
            )
            layoutSkill.addView(skillItemView)
        }

        // Set WorkExperienceList
        var workExperienceList: ArrayList<HCWorkExperience> = arrayListOf()
        for (work_experience_response in candidateDetail.candidate__work_experiences) {

            var workExperience = HCWorkExperience()
            workExperience.setExperienceId(work_experience_response.work_experience__work_experience_id)
            workExperience.setExperienceJobTitle(work_experience_response.work_experience__job_title)
            workExperience.setExperienceDescription(work_experience_response.work_experience__description)
            workExperience.setExperienceFrom(work_experience_response.work_experience__worked_from)
            workExperience.setExperienceTo(work_experience_response.work_experience__worked_to)
            workExperience.setBrandName(work_experience_response.brand__name)
            workExperience.setBrandLogoUrl(work_experience_response.asset__cloudinary_url)

            workExperienceList.add(workExperience)
        }
        workExperienceViewModel.setWorkExperienceList(workExperienceList)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_approve -> {
                val intent = Intent(this, ApproveActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.layout_reject -> {
                val intent = Intent(this, RejectActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_close -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
