package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.activities.HomeActivity
import de.hdodenhof.circleimageview.CircleImageView

class FeedbackDoneActivity : AppCompatActivity() {

    private lateinit var txtCandidateName: TextView
    private lateinit var txtCandidatJob: TextView
    private lateinit var imgCandidateAvatar: CircleImageView
    private lateinit var txtCongratulation: TextView

    private lateinit var btnBackToShortlist: Button
//    private lateinit var btnGiveAvailability: Button

    private var candidateName: String = ""
    private var candidateJob: String = ""
    private var candidateAvatar: String = ""
    private var event: Boolean = false
    private var isApprove: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_done)

        isApprove = intent.getBooleanExtra("isApprove", true).safeValue()
        candidateName = intent.getStringExtra("candidateName").safeValue()
        candidateAvatar = intent.getStringExtra("candidateAvatar").safeValue()
        candidateJob = intent.getStringExtra("candidateJob").safeValue()
        event = intent.getBooleanExtra("event", false)
        initUI()
    }

    private fun initUI() {
        txtCandidateName = findViewById(R.id.text_name)
        txtCandidatJob = findViewById(R.id.text_job)
        imgCandidateAvatar = findViewById(R.id.image_avatar)
        txtCongratulation = findViewById(R.id.text_congratulation)

        if (isApprove) {
            txtCandidateName.visibility = View.VISIBLE
            txtCandidatJob.visibility = View.VISIBLE
            imgCandidateAvatar.visibility = View.VISIBLE

            txtCandidateName.text = candidateName
            txtCandidatJob.text = candidateJob
            Glide.with(this).load(candidateAvatar).into(imgCandidateAvatar)

//        btnGiveAvailability = findViewById(R.id.button_give_interview_availability)
            if (event) {
                txtCongratulation.text = getString(R.string.feedback_success, candidateName)
            } else {
                txtCongratulation.text = getString(R.string.feedback_false)
            }
        } else {
            txtCandidateName.visibility = View.GONE
            txtCandidatJob.visibility = View.GONE
            imgCandidateAvatar.visibility = View.GONE
            txtCongratulation.text = getString(R.string.feedback_reject, candidateName)
        }


        btnBackToShortlist = findViewById(R.id.button_back_to_shortlist)
        btnBackToShortlist.setOnClickListener {
            HCGlobal.getInstance().currentIndex = 0
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("shortlistCashMode", false)
            startActivity(intent)
            finish()
        }

//        btnGiveAvailability.setOnClickListener {
//
//        }
    }
}
