package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.ui.HCBaseActivity
import com.hidden.client.ui.adapters.HCCandidateAdapter
import com.hidden.client.ui.viewmodels.HCCandidateViewModel

class HCCandidateActivity : HCBaseActivity(), View.OnClickListener {

    // RecyclerView for Candidate
    private lateinit var rvCandidate: RecyclerView
    private lateinit var candidateViewModel: HCCandidateViewModel
    private lateinit var candidateAdapter: HCCandidateAdapter

    private lateinit var imgClose: ImageView

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

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.image_close -> {
                finish()
            }
        }
    }
}
