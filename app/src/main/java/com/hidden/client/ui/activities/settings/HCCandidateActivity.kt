package com.hidden.client.ui.activities.settings

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCCandidateResponse
import com.hidden.client.datamodels.HCConsentResponse
import com.hidden.client.enums.Consent
import com.hidden.client.enums.UserType
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCCandidate
import com.hidden.client.ui.HCBaseActivity
import com.hidden.client.ui.adapters.HCCandidateAdapter
import com.hidden.client.ui.viewmodels.HCCandidateViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCCandidateActivity : HCBaseActivity() {

    // RecyclerView for Candidate
    private lateinit var rvCandidate: RecyclerView
    private lateinit var candidateViewModel: HCCandidateViewModel
    private lateinit var candidateAdapter: HCCandidateAdapter

    private lateinit var editSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)

        // Your Jobs RecyclerView
        rvCandidate = findViewById(R.id.recyclerview_candidates)
        candidateViewModel = ViewModelProviders.of(this).get(HCCandidateViewModel::class.java)
        candidateViewModel.getCandidateList().observe(this, Observer {candidateViewModels->
            candidateAdapter = HCCandidateAdapter(applicationContext, candidateViewModels)
            rvCandidate.layoutManager = LinearLayoutManager(applicationContext)
            rvCandidate.setHasFixedSize(true)

            rvCandidate.adapter = candidateAdapter
        })

        initCloseButton()

        editSearch = findViewById(R.id.edit_search)

        getCandidateList()
    }

    private fun getCandidateList() {

        val search = editSearch.text.toString()

        RetrofitClient.instance.getCandidateList(HCGlobal.getInstance(this).g_client.getBearerToken(), search)
            .enqueue(object: Callback<List<HCCandidateResponse>> {
                override fun onFailure(call: Call<List<HCCandidateResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<HCCandidateResponse>>,
                    response: Response<List<HCCandidateResponse>>
                ) {

                    if (response.isSuccessful) {
                        val candidateResponseList: List<HCCandidateResponse> = response.body()!!

                        var candidateList: ArrayList<HCCandidate> = arrayListOf()

                        for (candidateResponse in candidateResponseList) run {
                            var candidate: HCCandidate = HCCandidate()

                            candidate.setCandidateId(candidateResponse.candidate__candidate_id)

                            if (candidateResponse.asset_candidate__cloudinary_url == null) {
                                candidate.setCandidatePhotoUrl("")
                            } else {
                                candidate.setCandidatePhotoUrl(candidateResponse.asset_candidate__cloudinary_url)
                            }

                            candidate.setCandidateFirstName(candidateResponse.candidate__first_name)
                            candidate.setCandidateSurName(candidateResponse.candidate__surname)

                            candidateList.add(candidate)
                        }

                        candidateViewModel.setCandidateList(candidateList)

                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
