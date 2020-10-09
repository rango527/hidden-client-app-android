package com.hidden.client.ui.activities.shortlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.databinding.InterviewDetailBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.models.entity.InterviewEntity
import com.hidden.client.ui.custom.interview.InterviewFeedbackFragment
import com.hidden.client.ui.custom.interview.InterviewInfoFragment
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.InterviewDetailVM
import com.kaopiz.kprogresshud.KProgressHUD

class InterviewActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: InterviewDetailBinding
    private lateinit var viewModel: InterviewDetailVM

    private lateinit var progressDlg: KProgressHUD

    private lateinit var titleTextView: TextView
    private lateinit var infoTextView: TextView
    private lateinit var feedbackTextView: TextView

    private lateinit var fragmentFrameLayout: FrameLayout

    private lateinit var backButton: ImageButton

    private var processId: Int = 0
    private var interviewId: Int = 0
    private var jobId: Int = 0

    private var switchTagInfo = true

    private lateinit var interview: InterviewEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)

        processId = intent.getIntExtra("processId", 0)
        interviewId = intent.getIntExtra("interviewId", 0)
        jobId = intent.getIntExtra("jobId", 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_interview)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(InterviewDetailVM::class.java)

        binding.viewModel = viewModel

        initUI()

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        viewModel.interviewTitle.observe(this, Observer { title ->
            titleTextView.text = title
        })

        viewModel.interviewDetail.observe(this, Observer { interviewEntity ->
            interview = interviewEntity
            showFragment()
        })

        viewModel.loadInterviewDetail(processId, interviewId)

    }

    private fun initUI() {

        titleTextView = findViewById(R.id.interview_title)
        infoTextView = findViewById(R.id.interview_info)
        feedbackTextView = findViewById(R.id.interview_feedback)

        infoTextView.setOnClickListener(this)
        feedbackTextView.setOnClickListener(this)

        fragmentFrameLayout = findViewById(R.id.interview_fragment)

        backButton = findViewById(R.id.button_back)

        backButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.interview_info -> {
                // info tag clicked
                switchTagInfo = true
                showFragment()
            }
            R.id.interview_feedback -> {
                // feedback tag clicked
                switchTagInfo = false
                showFragment()
            }
            R.id.button_back -> {
                finish()
            }
        }
    }

    private fun showFragment() {

        if (switchTagInfo) {

            infoTextView.setBackgroundResource(R.drawable.button_round_white_1x)
            feedbackTextView.setBackgroundResource(0)

            supportFragmentManager.beginTransaction()
                .replace(R.id.interview_fragment,
                    InterviewInfoFragment(this, interview)
                ).commit()
        }
        else {

            infoTextView.setBackgroundResource(0)
            feedbackTextView.setBackgroundResource(R.drawable.button_round_white_1x)

            supportFragmentManager.beginTransaction()
                .replace(R.id.interview_fragment,
                    InterviewFeedbackFragment(this, interview)
                ).commit()
        }
    }
}
