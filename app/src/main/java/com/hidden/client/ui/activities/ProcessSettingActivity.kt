package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hidden.client.R
import com.hidden.client.databinding.ProcessSettingBinding
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessSettingVM
import com.kaopiz.kprogresshud.KProgressHUD

class ProcessSettingActivity : BaseActivity() {

    private lateinit var imgClose: ImageView

    private lateinit var imgInterviewersDescription: ImageView
    private lateinit var imgInterviewAdvancerDescription: ImageView
    private lateinit var imgOfferManagerDescription: ImageView

    private lateinit var imgAddRoleInterviewer: ImageView
    private lateinit var imgAddRoleInterviewAdvancer: ImageView
    private lateinit var imgAddRoleOfferManager: ImageView

    private lateinit var layoutProcessSettingNote: LinearLayout
    private lateinit var txtProcessSettingNote: TextView
    private lateinit var btnGotProcessSetting: Button

    private lateinit var binding: ProcessSettingBinding
    private lateinit var viewModel: ProcessSettingVM

    private lateinit var progressDlg: KProgressHUD

    private var processId: Int = 0
    private var jobId: Int = 0
    private var cashMode: Boolean = true

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processId = intent.getIntExtra("processId", 0)
        jobId = intent.getIntExtra("jobId", 0)
        cashMode = intent.getBooleanExtra("cashMode", true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_process_setting)

        binding.recyclerviewInterviewer.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewInterviewAdvancer.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewOfferManager.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ProcessSettingVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
                swipeContainer.isRefreshing = false
            }
        })

        // Observing for reloading after login success
        viewModel.navigateReload.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
//                viewModel.processId = this.processId
//                viewModel.loadProcessSetting(false)
                finish()
                overridePendingTransition( 0, 0);
                intent.putExtra("cashMode", false)
                startActivity(intent);
                overridePendingTransition( 0, 0);
            }
        })

        viewModel.processId = this.processId
        viewModel.loadProcessSetting(cashMode)

        initUI()

        swipeContainer = findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener {
            viewModel.loadProcessSetting(false)
        }
    }

    private fun initUI() {
        imgClose = findViewById(R.id.img_close)

        layoutProcessSettingNote = findViewById(R.id.layout_process_setting_note)
        if (AppPreferences.processSettingTipChecked) {
            layoutProcessSettingNote.visibility = View.GONE
        }
        txtProcessSettingNote = findViewById(R.id.text_process_setting_note)
        txtProcessSettingNote.text = Html.fromHtml(getString(R.string.process_setting_note))
        txtProcessSettingNote.setOnClickListener {
            val intent: Intent = Intent(this, JobSettingActivity::class.java)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        btnGotProcessSetting = findViewById(R.id.button_got_note)
        btnGotProcessSetting.setOnClickListener {
            layoutProcessSettingNote.visibility = View.GONE
            AppPreferences.processSettingTipChecked =  true
        }

        imgInterviewersDescription = findViewById(R.id.img_interviewer_tip)
        imgInterviewAdvancerDescription = findViewById(R.id.img_interview_advancer_tip)
        imgOfferManagerDescription = findViewById(R.id.img_offer_manager_tip)

        imgAddRoleInterviewer = findViewById(R.id.img_add_role_to_interviewer)
        imgAddRoleInterviewAdvancer = findViewById(R.id.img_add_role_to_interview_advancer)
        imgAddRoleOfferManager = findViewById(R.id.img_add_role_to_offer_manager)

        imgClose.setOnClickListener { finish() }

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
        imgAddRoleInterviewer.setOnClickListener {
            val intent =
                if (viewModel.isUserManager) Intent(
                    HCGlobal.getInstance().currentActivity,
                    ProcessAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    ProcessUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER.value)
            intent.putExtra("processId", processId)
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
                    ProcessAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    ProcessUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER_ADVANCER.value)
            intent.putExtra("processId", processId)
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
                    ProcessAddRoleActivity::class.java
                ) else Intent(
                    HCGlobal.getInstance().currentActivity,
                    ProcessUserManagerActivity::class.java
                )
            intent.putExtra("reviewType", Enums.ReviewerType.OFFER_MANAGER.value)
            intent.putExtra("processId", processId)
            intent.putExtra("jobId", jobId)
            startActivity(intent)
            overridePendingVTransitionEnter()

            if (viewModel.isUserManager) {
                finish()
            }
        }
    }

    fun removeRoleFromProcessSetting(reviewer: ReviewerEntity) {
        val role: String  = when (reviewer.reviewerType) {
            Enums.ReviewerType.INTERVIEWER.value -> Enums.ReviewerTypeText.INTERVIEWER.value
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> Enums.ReviewerTypeText.INTERVIEWER_ADVANCER.value
            Enums.ReviewerType.OFFER_MANAGER.value -> Enums.ReviewerTypeText.OFFER_MANAGER.value
            else -> ""
        }
        viewModel.removeUserRoleToProcessSetting(reviewer.parentId, role, reviewer.clientId, reviewer.id)
    }
}
