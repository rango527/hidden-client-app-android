package com.hidden.client.ui.activities.process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.hidden.client.R
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.activities.shortlist.FeedbackActivity

class HSJumpStageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBackDashboard: ImageButton
    private lateinit var btnFurtherStage: Button
    private lateinit var btnFinalStage: Button
    private lateinit var btnAddInterview: Button
    private lateinit var btnMakeOffer: Button

    private var interviewId: Int = 0
    private var processId: Int = 0
    private var isApprove: Boolean = true
    private var candidateName: String = ""
    private var nextStages:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jump_stage)

        isApprove = intent.getBooleanExtra("isApprove", true)
        processId = intent.getIntExtra("processId", 0)
        candidateName = intent.getStringExtra("candidateName").safeValue()
        nextStages = intent.getStringExtra("nextStages").safeValue()
        interviewId = intent.getIntExtra("interviewId", 0)

        btnBackDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackDashboard.setOnClickListener(this)

        btnFurtherStage = findViewById(R.id.btn_further_stage)
        if (nextStages.contains("TO_FURTHER_STAGE", ignoreCase = false)) {
            btnFurtherStage.visibility = View.VISIBLE
            btnFurtherStage.setOnClickListener(this)
        }

        btnFinalStage = findViewById(R.id.btn_final_stage)
        if (nextStages.contains("TO_FINAL_STAGE", ignoreCase = false)) {
            btnFinalStage.visibility = View.VISIBLE
            btnFinalStage.setOnClickListener(this)
        }

        btnAddInterview = findViewById(R.id.btn_add_interview_stage)
        if (nextStages.contains("ADD_INTERVIEW", ignoreCase = false)) {
            btnAddInterview.visibility = View.VISIBLE
            btnAddInterview.setOnClickListener(this)
        }

        btnMakeOffer = findViewById(R.id.btn_make_offer)
        if (nextStages.contains("MAKE_OFFER", ignoreCase = false)) {
            btnMakeOffer.visibility = View.VISIBLE
            btnMakeOffer.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_dashboard -> {
                finish()
            }
            R.id.btn_further_stage -> {
                val intent = Intent(this, HSGiveFeedbackActivity::class.java)
                intent.putExtra("processId", processId)
                startActivity(intent)
            }
            R.id.btn_final_stage -> {
                val intent = Intent(this, HSGiveFeedbackActivity::class.java)
                intent.putExtra("processId", processId)
                startActivity(intent)
            }
            R.id.btn_add_interview_stage -> {
                val intent = Intent(this, HSGiveFeedbackActivity::class.java)
                intent.putExtra("processId", processId)
                startActivity(intent)
            }
            R.id.btn_make_offer -> {
                val intent = Intent(this, HSGiveFeedbackActivity::class.java)
                intent.putExtra("processId", processId)
                startActivity(intent)
            }
        }
    }
}
