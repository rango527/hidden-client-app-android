package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var btnGiveAvailability: Button

    private var candidateName: String = ""
    private var candidateJob: String = ""
    private var candidateAvatar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_done)

        candidateName = intent.getStringExtra("candidateName").safeValue()
        candidateAvatar = intent.getStringExtra("candidateAvatar").safeValue()
        candidateJob = intent.getStringExtra("candidateJob").safeValue()

        initUI()
    }

    private fun initUI() {
        txtCandidateName = findViewById(R.id.text_name)
        txtCandidatJob = findViewById(R.id.text_job)
        imgCandidateAvatar = findViewById(R.id.image_avatar)

        txtCandidateName.text = candidateName
        txtCandidatJob.text = candidateJob
        Glide.with(this).load(candidateAvatar).into(imgCandidateAvatar)

        btnBackToShortlist = findViewById(R.id.button_back_to_shortlist)
        btnGiveAvailability = findViewById(R.id.button_give_interview_availability)

        btnBackToShortlist.setOnClickListener {
            HCGlobal.getInstance().currentIndex = 0
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("shortlistCashMode", false)
            startActivity(intent)
            finish()
        }

        btnGiveAvailability.setOnClickListener {

        }
    }
}
