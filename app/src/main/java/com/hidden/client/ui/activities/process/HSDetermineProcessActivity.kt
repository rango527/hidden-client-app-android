package com.hidden.client.ui.activities.process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.hidden.client.R
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.activities.ProcessActivity

class HSDetermineProcessActivity : AppCompatActivity(), View.OnClickListener {

    private var processId: Int = 0
    private var candidateName: String? = ""
    private var nextStages: String? = ""
    private var interviewId: Int = 0

    private lateinit var btnProcessNextStage: Button
    private lateinit var btnProcessReject: Button
    private lateinit var btnBackDashboard: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_determine_process)

        processId = intent.getIntExtra("processId", 0)
        candidateName = intent.getStringExtra("candidateName").safeValue()
        nextStages = intent.getStringExtra("nextStages").safeValue()
        interviewId = intent.getIntExtra("interviewId", 0)

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
                val intent = Intent(this, ProcessActivity::class.java)
                intent.putExtra("processId", processId)
                intent.putExtra("cashMode", true)
                startActivity(intent)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
            }

            R.id.btn_process_next_stage -> {
                val intent = Intent(this, HSJumpStageActivity::class.java)

                intent.putExtra("processId", processId)
                intent.putExtra("isApprove", true)
                intent.putExtra("candidateName", candidateName)
                intent.putExtra("nextStages", nextStages)
                intent.putExtra("interviewId", interviewId)

                startActivity(intent)
            }

            R.id.btn_process_reject -> {
                val intent = Intent(this, HSGiveFeedbackActivity::class.java)
                intent.putExtra("processId", processId)
                intent.putExtra("isApprove", false)
                intent.putExtra("processFeedback", true)
                startActivity(intent)
            }
        }
    }
}


