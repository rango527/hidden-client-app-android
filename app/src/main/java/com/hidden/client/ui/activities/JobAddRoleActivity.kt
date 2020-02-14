package com.hidden.client.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.UserManagerListBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobSettingVM
import com.kaopiz.kprogresshud.KProgressHUD

class JobAddRoleActivity : AppCompatActivity() {

    private lateinit var binding: UserManagerListBinding
    private lateinit var viewModel: JobSettingVM

    private lateinit var imgClose: ImageView
    private lateinit var txtReviewType: TextView
    private lateinit var txtAddTeamMember: TextView

    private lateinit var progressDlg: KProgressHUD

    private lateinit var btnSave: Button
    private lateinit var editSearch: EditText
    private lateinit var layoutCascade: LinearLayout

    private lateinit var txtAddNewOnly: TextView
    private lateinit var txtAddNewAndOld: TextView

    private var cascadeType: Int = Enums.AddUserRoleJobSetting.NEW_PROCESS_ONLY.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance().currentActivity = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_add_role)
        binding.recyclerviewUserManager.layoutManager = LinearLayoutManager(this)

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

        val jobId: Int = intent.getIntExtra("jobId", 0)
        val reviewType: Int = intent.getIntExtra("reviewType", 0)

        viewModel.reviewerTypeNum = reviewType
        viewModel.jobId = jobId

        // Init View
        initUI()
        initShortlistReviewerText(reviewType)
    }

    fun setSaveButtonEnable() {
        var enable = false;
        for (userManager in viewModel.userManagerListAdapter.getUserManagerList()) {
            if (userManager.tick) {
                enable = true
                break;
            }
        }
        btnSave.isEnabled = enable

        if (! enable) {
            layoutCascade.visibility = View.GONE
        }
    }

    private fun initUI() {
        editSearch = findViewById(R.id.edit_search)
        editSearch.doAfterTextChanged { text -> viewModel.search = text }

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }

        txtAddTeamMember = findViewById(R.id.text_add_team_member)
        txtAddTeamMember.setOnClickListener {
            val intent = Intent(this, InviteTeamMember::class.java)
            startActivity(intent)
        }

        btnSave = findViewById(R.id.button_save)
        btnSave.setOnClickListener {
            if (layoutCascade.visibility == View.GONE) {
                layoutCascade.visibility = View.VISIBLE
            } else {
                val userManagerList = viewModel.userManagerListAdapter.getUserManagerList()
                val userManagerIdList: MutableList<Int> = mutableListOf()
                for (userManager in userManagerList) {
                    if (userManager.tick) {
                        userManagerIdList.add(userManager.user.clientId)
                    }
                }
            }

        }

        layoutCascade = findViewById(R.id.layout_cascade)

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
    }

    private fun initShortlistReviewerText(reviewType: Int) {
        txtReviewType = findViewById(R.id.text_review_type)
        when (reviewType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.shortlist_reviewer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.interviewer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.interviewer_advancer,
                    1, 1
                )
            Enums.ReviewerType.OFFER_MANAGER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.offer_manager,
                    1, 1
                )
            else -> txtReviewType.text = resources.getQuantityString(
                R.plurals.shortlist_reviewer,
                1, 1
            )
        }
    }
}
