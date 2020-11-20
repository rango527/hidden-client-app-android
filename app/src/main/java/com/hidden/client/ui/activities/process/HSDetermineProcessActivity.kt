package com.hidden.client.ui.activities.process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.shortlist.FeedbackActivity
import com.hidden.client.ui.fragments.process.ProcessTimelineFragment

class HSDetermineProcessActivity : AppCompatActivity(), View.OnClickListener {

    private var from: String? = ""
    private var processId: Int = 0
    private var jobId: Int = 0
    private var interviewId: Int = 0
    private var candidateName: String? = ""

    private lateinit var btnProcessNextStage: Button
    private lateinit var btnProcessReject: Button
    private lateinit var btnBackDashboard: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_determine_process)

        from = intent.getStringExtra("from")
        processId = intent.getIntExtra("processId", 0)
        jobId = intent.getIntExtra("jobId", 0)
        interviewId = intent.getIntExtra("interviewId", 0)
        candidateName = intent.getStringExtra("candidateName")

        btnProcessNextStage = findViewById(R.id.btn_process_next_stage)
        btnProcessNextStage.setOnClickListener(this)

        btnProcessReject = findViewById(R.id.btn_process_reject)
        btnProcessReject.setOnClickListener(this)

        btnBackDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackDashboard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_dashboard -> {
                finish()
            }

            R.id.btn_process_next_stage -> {
                val intent = Intent(this, HSJumpStageActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_process_reject -> {
                val intent = Intent(this, FeedbackActivity::class.java)

                intent.putExtra("processId", processId)
//                intent.putExtra("avatarName", candidateName)
                intent.putExtra("isApprove", false)
                intent.putExtra("candidateName", candidateName)
//                intent.putExtra("candidateAvatar", viewModel.getShortlistCandidate().assetUrl)
//                intent.putExtra(
//                    "candidateJob",
//                    String.format(
//                        getString(R.string.job_and_location),
//                        viewModel.getShortlistCandidate().jobTitle,
//                        viewModel.getShortlistCandidate().jobCityName
//                    )
//                )

                startActivity(intent)
            }
        }
    }
}


