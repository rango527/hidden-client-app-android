package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.JobSettingBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobSettingVM
import com.kaopiz.kprogresshud.KProgressHUD

class JobSettingActivity : BaseActivity() {

    private lateinit var imgClose: ImageView

    private lateinit var imgShortlistReviewerTypeDescription: ImageView
    private lateinit var imgShortlistReviewerDescription: ImageView
    private lateinit var imgInterviewersDescription: ImageView
    private lateinit var imgInterviewAdvancerDescription: ImageView
    private lateinit var imgOfferManagerDescription: ImageView

    private lateinit var imgAddRoleShortlistReviewer: ImageView
    private lateinit var imgAddRoleInterviewer: ImageView
    private lateinit var imgAddRoleInterviewAdvancer: ImageView
    private lateinit var imgAddRoleOfferManager: ImageView

    private lateinit var binding: JobSettingBinding
    private lateinit var viewModel: JobSettingVM

    private lateinit var progressDlg: KProgressHUD

    private var jobId: Int = 0
    private var cashMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        jobId = intent.getIntExtra("jobId", 0)
        cashMode = intent.getBooleanExtra("cashMode", true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_setting)

        binding.recyclerviewShortlistReviewer.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewInterviewer.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewInterviewAdvancer.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewOfferManager.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(JobSettingVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        viewModel.jobId = this.jobId
        viewModel.loadJobSetting(cashMode)

        initUI()
    }

    private fun initUI() {
        imgClose = findViewById(R.id.img_close)

        imgShortlistReviewerTypeDescription = findViewById(R.id.img_shortlist_review_text_tip)
        imgShortlistReviewerDescription = findViewById(R.id.img_shortlist_reviewer_tip)
        imgInterviewersDescription = findViewById(R.id.img_interviewer_tip)
        imgInterviewAdvancerDescription = findViewById(R.id.img_interview_advancer_tip)
        imgOfferManagerDescription = findViewById(R.id.img_offer_manager_tip)

        imgAddRoleShortlistReviewer = findViewById(R.id.img_add_role_to_shortlist_reviewer)
        imgAddRoleInterviewer = findViewById(R.id.img_add_role_to_interviewer)
        imgAddRoleInterviewAdvancer = findViewById(R.id.img_add_role_to_interview_advancer)
        imgAddRoleOfferManager = findViewById(R.id.img_add_role_to_offer_manager)

        imgClose.setOnClickListener { finish() }

        // Description
        imgShortlistReviewerTypeDescription.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", 0)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        imgShortlistReviewerDescription.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.SHORTLIST_REVIEWER.value)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        imgInterviewersDescription.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER.value)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        imgInterviewAdvancerDescription.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER_ADVANCER.value)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        imgOfferManagerDescription.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.OFFER_MANAGER.value)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        // Add Role
        imgAddRoleShortlistReviewer.setOnClickListener {
            val intent =
                if (viewModel.isUserManager) Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.SHORTLIST_REVIEWER.value)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()
            if (viewModel.isUserManager) {
                finish()
            }
        }

        imgAddRoleInterviewer.setOnClickListener {
            val intent =
                if (viewModel.isUserManager) Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER.value)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()
            if (viewModel.isUserManager) {
                finish()
            }
        }

        imgAddRoleInterviewAdvancer.setOnClickListener {
            val intent =
                if (viewModel.isUserManager) Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER_ADVANCER.value)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()
            if (viewModel.isUserManager) {
                finish()
            }
        }

        imgAddRoleOfferManager.setOnClickListener {
            val intent =
                if (viewModel.isUserManager) Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    JobUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.OFFER_MANAGER.value)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()
            if (viewModel.isUserManager) {
                finish()
            }
        }
    }

    fun getViewModel(): JobSettingVM {
        return viewModel
    }
}
