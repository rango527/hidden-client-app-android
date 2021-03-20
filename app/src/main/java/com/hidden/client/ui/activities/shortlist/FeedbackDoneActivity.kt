package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var txtCandidateJob: TextView
    private lateinit var imgCandidateAvatar: CircleImageView
    private lateinit var txtCongratulation: TextView

    private lateinit var btnBackToShortlist: Button

    private var candidateName: String = ""
    private var candidateJob: String = ""
    private var candidateAvatar: String = ""
    private var statusAfterVote: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_done)

        statusAfterVote = intent.getStringExtra("statusAfterVote").safeValue()
        candidateName = intent.getStringExtra("candidateName").safeValue()
        candidateAvatar = intent.getStringExtra("candidateAvatar").safeValue()
        candidateJob = intent.getStringExtra("candidateJob").safeValue()
        initUI()
    }

    private fun initUI() {
        txtCandidateName = findViewById(R.id.text_name)
        txtCandidateJob = findViewById(R.id.text_job)
        imgCandidateAvatar = findViewById(R.id.image_avatar)
        txtCongratulation = findViewById(R.id.text_congratulation)

        when (statusAfterVote) {
            "ACCEPTED" -> {
                txtCandidateName.visibility = View.VISIBLE
                txtCandidateJob.visibility = View.VISIBLE
                imgCandidateAvatar.visibility = View.VISIBLE

                txtCandidateName.text = candidateName
                txtCandidateJob.text = candidateJob
                Glide.with(this).load(candidateAvatar).into(imgCandidateAvatar)

                txtCongratulation.text = getString(R.string.feedback_success, candidateName)
            }
            "REJECTED" -> {
                txtCandidateName.visibility = View.GONE
                txtCandidateJob.visibility = View.GONE
                imgCandidateAvatar.visibility = View.GONE
                txtCongratulation.text = getString(R.string.feedback_reject, candidateName)
            }
            else -> {
                txtCandidateName.visibility = View.GONE
                txtCandidateJob.visibility = View.GONE
                imgCandidateAvatar.visibility = View.GONE
                txtCongratulation.text = getString(R.string.feedback_pending)
            }
        }

        btnBackToShortlist = findViewById(R.id.button_back_to_shortlist)
        btnBackToShortlist.setOnClickListener {
            HCGlobal.getInstance().currentIndex = 0
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("shortlistCashMode", false)
            startActivity(intent)
            finish()
        }
    }
}
