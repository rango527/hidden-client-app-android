package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCCandidateDetailResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.HCBrandAdapter
import com.hidden.client.ui.adapters.HCWorkExperienceAdapter
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateDetailActivity : BaseActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)

        initCloseButton()

        val categoryId: String = intent.getStringExtra("category_id")


        imgPhoto = findViewById(R.id.image_photo)
        textName = findViewById(R.id.text_name)
        textLocation = findViewById(R.id.text_location)
        textJobTitle = findViewById(R.id.text_job_title)
        textSnapshot = findViewById(R.id.text_snapshot)

        // Brand RecyclerView
        rvBrand = findViewById(R.id.recyclerview_brand)
        brandViewModel = ViewModelProviders.of(this).get(HCBrandViewModel::class.java)
        brandViewModel.getBrandList().observe(this, Observer {brandViewModel ->
            brandAdapter = HCBrandAdapter(this, brandViewModel)

            layoutBrandManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvBrand.layoutManager = layoutBrandManager
            rvBrand.setHasFixedSize(true)

            rvBrand.adapter = brandAdapter
        })

        // Work Experience RecyclerView
        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)
        workExperienceViewModel = ViewModelProviders.of(this).get(HCWorkExperienceViewModel::class.java)
        workExperienceViewModel.getWorkExperienceList().observe(this, Observer {workExperienceViewModels->
            workExperienceAdapter = HCWorkExperienceAdapter(this, workExperienceViewModels)

            rvWorkExperience.layoutManager = LinearLayoutManager(this)
            rvWorkExperience.setHasFixedSize(true)

            rvWorkExperience.adapter = workExperienceAdapter
        })

        RetrofitClient.instance.getCandidateDetail(AppPreferences.apiAccessToken, categoryId)
            .enqueue(object: Callback<HCCandidateDetailResponse> {
                override fun onFailure(call: Call<HCCandidateDetailResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCCandidateDetailResponse>,
                    response: Response<HCCandidateDetailResponse>
                ) {

                    if (response.isSuccessful) {

                        var candidateDetail = response.body()!!

                        val photoUrl = candidateDetail.asset__cloudinary_url
                        Glide.with(this@CandidateDetailActivity).load(photoUrl).into(imgPhoto)

                        textName.setText(candidateDetail.candidate__full_name)
                        textLocation.setText(candidateDetail.candidate_city__name)
                        textJobTitle.setText(candidateDetail.job_title_1__name + " | " + candidateDetail.job_title_2__name + " | " + candidateDetail.job_title_3__name)
                        textSnapshot.setText(candidateDetail.candidate__hidden_says)

                        // Set Brand List
                        var candidateBrandList : ArrayList<HCBrand> = arrayListOf()
                        for (candidate_brand_response in candidateDetail.candidate__brands) {
                            candidateBrandList.add(HCBrand(candidate_brand_response.asset__cloudinary_url))
                        }
                        brandViewModel.setBrandList(candidateBrandList)

                        // Skill Layout
                        layoutSkill = findViewById(R.id.layout_skills)

                        for (candidate_skill in candidateDetail.candidate__skills) {
                            var skillItemView = SkillItemView(this@CandidateDetailActivity, candidate_skill.skill__name, candidate_skill.candidate_skill__ranking)
                            layoutSkill.addView(skillItemView)
                        }

                        // Set WorkExperienceList
                        var workExperienceList : ArrayList<HCWorkExperience> = arrayListOf()
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

                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })

    }
}
