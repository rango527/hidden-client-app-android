package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.hidden.client.R
import com.hidden.client.helpers.Enums

class JobReviewerType : AppCompatActivity() {

    private lateinit var imgClose: ImageView
    private lateinit var txtReviewType: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_reviewer_type)

        imgClose = findViewById(R.id.img_close)
        txtReviewType = findViewById(R.id.text_review_type)

        val reviewType: Int = intent.getIntExtra("reviewType", 0)

        imgClose.setOnClickListener { finish() }

        when (reviewType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value -> txtReviewType.text = getString(R.string.shortlist_reviewers_description)
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> txtReviewType.text = getString(R.string.interview_advancers_description)
            Enums.ReviewerType.INTERVIEWER.value -> txtReviewType.text = getString(R.string.interviewers_description)
            Enums.ReviewerType.OFFER_MANAGER.value -> txtReviewType.text = getString(R.string.offer_manager_description)
            else -> txtReviewType.text = getString(R.string.shortlist_review_type_description)
        }
    }
}
