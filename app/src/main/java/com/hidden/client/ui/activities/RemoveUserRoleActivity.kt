package com.hidden.client.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobSettingVM
import com.kaopiz.kprogresshud.KProgressHUD

class RemoveUserRoleActivity : BaseActivity() {

    private lateinit var imgClose: ImageView
    private lateinit var txtRemoveUserNote: TextView
    private lateinit var imgRemoveUserNote: ImageView

    private lateinit var txtAddNewOnly: TextView
    private lateinit var txtAddNewAndOld: TextView

    private var cascadeType: Int = Enums.AddUserRoleJobSetting.NEW_PROCESS_ONLY.value

    private lateinit var btnRemove: Button

    private var clientName: String = ""
    private var reviewerId: Int = 0
    private var reviewerType: Int = 0
    private var clientId: Int = 0
    private var jobId: Int = 0

    private lateinit var viewModel: JobSettingVM

    private lateinit var progressDlg: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_user_role)

        clientName = intent.getStringExtra("clientName").safeValue()
        reviewerId = intent.getIntExtra("reviewerId", 0)
        reviewerType = intent.getIntExtra("reviewType", 0)
        clientId = intent.getIntExtra("clientId", 0)
        jobId = intent.getIntExtra("jobId", 0)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(JobSettingVM::class.java)

        // Observing for jumping JobSetting after remove role
        viewModel.navigateToJobSetting.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                backToJobSetting(false)
            }
        })

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        initUI()
    }

    private fun initUI() {
        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener {
            backToJobSetting(true)
        }

        txtRemoveUserNote = findViewById(R.id.text_remove_note)

        val reviewerText: String = when (reviewerType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value ->
                resources.getQuantityString(
                    R.plurals.shortlist_reviewer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value ->
                resources.getQuantityString(
                    R.plurals.interviewer_advancer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER.value ->
                resources.getQuantityString(
                    R.plurals.interviewer,
                    1, 1
                )
            Enums.ReviewerType.OFFER_MANAGER.value ->
                resources.getQuantityString(
                    R.plurals.offer_manager,
                    1, 1
                )
            else -> resources.getQuantityString(
                R.plurals.shortlist_reviewer,
                1, 1
            )
        }

        txtRemoveUserNote.text =
            String.format(getString(R.string.remove_role_note), clientName, reviewerText)

        imgRemoveUserNote = findViewById(R.id.image_remove_user_tip)
        imgRemoveUserNote.setOnClickListener {
            val intent = Intent(this, JobReviewerTypeActivity::class.java)
            intent.putExtra("reviewType", reviewerType)
            startActivity(intent)
            overridePendingVTransitionEnter()
        }

        txtAddNewOnly = findViewById(R.id.text_new_only)
        txtAddNewAndOld = findViewById(R.id.text_new_old)

        txtAddNewOnly.setOnClickListener {
            txtAddNewAndOld.setBackgroundColor(Color.TRANSPARENT)
            txtAddNewOnly.setBackgroundResource(R.drawable.button_round_white_1x)
            cascadeType = Enums.AddUserRoleJobSetting.NEW_PROCESS_ONLY.value
        }

        txtAddNewAndOld.setOnClickListener {
            txtAddNewOnly.setBackgroundColor(Color.TRANSPARENT)
            txtAddNewAndOld.setBackgroundResource(R.drawable.button_round_white_1x)
            cascadeType = Enums.AddUserRoleJobSetting.INCLUDE_OLD_PROCESS.value
        }

        btnRemove = findViewById(R.id.remove_user_role)
        btnRemove.setOnClickListener {

            val role: String = when (reviewerType) {
                Enums.ReviewerType.SHORTLIST_REVIEWER.value -> Enums.ReviewerTypeText.SHORTLIST_REVIEWER.value
                Enums.ReviewerType.INTERVIEWER.value -> Enums.ReviewerTypeText.INTERVIEWER.value
                Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> Enums.ReviewerTypeText.INTERVIEWER_ADVANCER.value
                Enums.ReviewerType.OFFER_MANAGER.value -> Enums.ReviewerTypeText.OFFER_MANAGER.value
                else -> ""
            }

            viewModel.removeUserRoleToJobSetting(
                jobId,
                role,
                clientId,
                this.cascadeType != Enums.AddUserRoleJobSetting.NEW_PROCESS_ONLY.value
            )
        }
    }

    override fun onBackPressed() {
        backToJobSetting(true)
    }

    private fun backToJobSetting(cashMode: Boolean) {
        val intent = Intent(this, JobSettingActivity::class.java)
        intent.putExtra("jobId", jobId)
        intent.putExtra("cashMode", cashMode)
        startActivity(intent)
        overridePendingVTransitionEnter()
    }
}
