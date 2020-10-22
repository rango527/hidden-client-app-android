package com.hidden.client.ui.activities.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.datamodels.HCCandidateDetailResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.HCBrandAdapter
import com.hidden.client.ui.adapters.HCWorkExperienceAdapter
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_candidate_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateDetailActivity : BaseActivity() {

    private lateinit var imgPhoto: CircleImageView
    private lateinit var textName: TextView
    private lateinit var textLocation: TextView
    private lateinit var textJobTitle: TextView
    private lateinit var textSnapshotTitle: TextView
    private lateinit var textSnapshot: TextView
    private lateinit var textSkillTitle: TextView
    private lateinit var layoutSkill: FlexboxLayout

    // RecyclerView for Brand
    private lateinit var textBrandTitle: TextView
    private lateinit var rvBrand: RecyclerView
    private lateinit var brandViewModel: HCBrandViewModel
    private lateinit var brandAdapter: HCBrandAdapter
    private lateinit var layoutBrandManager: RecyclerView.LayoutManager

    // RecyclerView for Work Experience
    private lateinit var textWorkExperienceTitle: TextView
    private lateinit var rvWorkExperience: RecyclerView
    private lateinit var workExperienceViewModel: HCWorkExperienceViewModel
    private var workExperienceList: MutableList<HCWorkExperience> = mutableListOf()
    private lateinit var workExperienceAdapter: HCWorkExperienceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)

        initCloseButton()

        val categoryId: String = intent.getStringExtra("category_id")!!

        imgPhoto = findViewById(R.id.image_photo)
        textName = findViewById(R.id.text_name)
        textLocation = findViewById(R.id.text_location)
        textJobTitle = findViewById(R.id.text_job_title)
        textSnapshotTitle = findViewById(R.id.text_snapshot_title)
        textSnapshot = findViewById(R.id.text_snapshot)

        textBrandTitle = findViewById(R.id.text_brand_title)
        rvBrand = findViewById(R.id.recyclerview_brand)

        textWorkExperienceTitle = findViewById(R.id.text_work_experience_title)
        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)

        textSkillTitle = findViewById(R.id.text_skills_title)
        layoutSkill = findViewById(R.id.layout_skills)

        // Brand RecyclerView
        textBrandTitle = findViewById(R.id.text_brand_title)
        rvBrand = findViewById(R.id.recyclerview_brand)
        brandViewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(HCBrandViewModel::class.java)
        brandViewModel.getBrandList().observe(this, Observer {brandViewModel ->
            brandAdapter = HCBrandAdapter(brandViewModel)

            layoutBrandManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvBrand.layoutManager = layoutBrandManager
            rvBrand.setHasFixedSize(true)

            rvBrand.adapter = brandAdapter
        })

        // Work Experience RecyclerView
//        textWorkExperienceTitle = findViewById(R.id.text_work_experience_title)
//        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)
//
//        workExperienceViewModel = ViewModelProviders.of(this).get(HCWorkExperienceViewModel::class.java)
//        workExperienceViewModel.getWorkExperienceList().observe(this, Observer {workExperienceViewModels->
//            workExperienceAdapter = HCWorkExperienceAdapter(workExperienceViewModels)
//
//            rvWorkExperience.layoutManager = LinearLayoutManager(this)
//            rvWorkExperience.setHasFixedSize(true)
//
//            rvWorkExperience.adapter = workExperienceAdapter
//        })

        RetrofitClient.instance.getCandidateDetail(AppPreferences.apiAccessToken, categoryId)
            .enqueue(object: Callback<HCCandidateDetailResponse> {
                override fun onFailure(call: Call<HCCandidateDetailResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<HCCandidateDetailResponse>,
                    response: Response<HCCandidateDetailResponse>
                ) {

                    if (response.isSuccessful) {

                        val candidateDetail = response.body()!!

                        val photoUrl = candidateDetail.asset__cloudinary_url
                        Glide.with(this@CandidateDetailActivity).load(photoUrl).into(imgPhoto)

                        textName.text = candidateDetail.candidate__full_name
                        textLocation.text = candidateDetail.candidate_city__name
                        textJobTitle.text = candidateDetail.job_title_1__name + " | " + candidateDetail.job_title_2__name + " | " + candidateDetail.job_title_3__name

                        // Set Snapshot
                        if (candidateDetail.candidate__hidden_says.isNullOrEmpty()) {
                            textSnapshotTitle.visibility = View.GONE
                            textSnapshot.visibility = View.GONE
                        } else {
                            textSnapshotTitle.visibility = View.VISIBLE
                            textSnapshot.visibility = View.VISIBLE
                            textSnapshot.text = candidateDetail.candidate__hidden_says
                        }

                        // Set Brand List
                        if (candidateDetail.candidate__brands.isNullOrEmpty()) {
                            textBrandTitle.visibility = View.GONE
                            rvBrand.visibility = View.GONE
                        } else {
                            textBrandTitle.visibility = View.VISIBLE
                            rvBrand.visibility = View.VISIBLE

                            val candidateBrandList : ArrayList<HCBrand> = arrayListOf()
                            for (candidate_brand_response in candidateDetail.candidate__brands) {
                                candidateBrandList.add(HCBrand(candidate_brand_response.asset__cloudinary_url))
                            }

                            brandViewModel.setBrandList(candidateBrandList)
                        }

                        // Skill Layout
                        if (candidateDetail.candidate__skills.isNullOrEmpty()) {
                            textSkillTitle.visibility = View.GONE
                            layoutSkill.visibility = View.GONE
                        } else {
                            textSkillTitle.visibility = View.VISIBLE
                            layoutSkill.visibility = View.VISIBLE

                            for (candidate_skill in candidateDetail.candidate__skills) {
                                val skillItemView = SkillItemView(
                                    this@CandidateDetailActivity,
                                    candidate_skill.skill__name,
                                    candidate_skill.candidate_skill__ranking
                                )
                                layoutSkill.addView(skillItemView)
                            }
                        }

                        // Set WorkExperienceList
                        if (candidateDetail.candidate__work_experiences.isNullOrEmpty()) {
                            textWorkExperienceTitle.visibility = View.GONE
                            rvWorkExperience.visibility = View.GONE
                        } else {
                            textWorkExperienceTitle.visibility = View.VISIBLE
                            rvWorkExperience.visibility = View.VISIBLE

//                            val workExperienceList: ArrayList<HCWorkExperience> = arrayListOf()
//                            for (work_experience_response in candidateDetail.candidate__work_experiences) {
//
//                                val workExperience = HCWorkExperience()
//                                workExperience.setExperienceId(work_experience_response.work_experience__work_experience_id)
//                                workExperience.setExperienceJobTitle(work_experience_response.work_experience__job_title)
//                                workExperience.setExperienceDescription(work_experience_response.work_experience__description)
//                                workExperience.setExperienceFrom(work_experience_response.work_experience__worked_from)
//                                workExperience.setExperienceTo(work_experience_response.work_experience__worked_to)
//                                workExperience.setBrandName(work_experience_response.brand__name)
//                                workExperience.setBrandLogoUrl(work_experience_response.asset__cloudinary_url)
//
//                                workExperienceList.add(workExperience)
//                            }
//                            workExperienceViewModel.setWorkExperienceList(workExperienceList)
                        }

                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })

    }
}
