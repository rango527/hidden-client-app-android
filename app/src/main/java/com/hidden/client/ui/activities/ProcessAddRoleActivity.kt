package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.AddProcessUserRoleBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessAddUserRoleVM
import com.kaopiz.kprogresshud.KProgressHUD

class ProcessAddRoleActivity : AppCompatActivity() {

    private lateinit var binding: AddProcessUserRoleBinding
    private lateinit var viewModel: ProcessAddUserRoleVM

    private lateinit var imgClose: ImageButton
    private lateinit var txtReviewType: TextView
    private lateinit var txtAddTeamMember: TextView

    private lateinit var progressDlg: KProgressHUD

    private lateinit var btnSave: Button
    private lateinit var editSearch: EditText

    private var processId: Int = 0
    private var reviewType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance().currentActivity = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_process_add_role)
        binding.recyclerviewUserManager.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ProcessAddUserRoleVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        processId = intent.getIntExtra("processId", 0)
        reviewType = intent.getIntExtra("reviewType", 0)

        // Observing for jumping ProcessSetting Activity after add new role
        viewModel.navigateToProcessSetting.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        })

        viewModel.loadAvailableUsers(processId, Utility.getReviewTextFromType(reviewType))

        // Init View
        initUI()
        initShortlistReviewerText()
    }

    fun setSaveButtonEnable() {
        var enable = false;
        for (userManager in viewModel.roleAvailableUserListAdapter.getRoleAvailableUserList()) {
            if (userManager.tick) {
                enable = true
                break;
            }
        }
        btnSave.isEnabled = enable
    }

    private fun initUI() {
        editSearch = findViewById(R.id.edit_search)
        editSearch.doAfterTextChanged { text -> viewModel.search = text }

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }

        txtAddTeamMember = findViewById(R.id.text_add_team_member)
        txtAddTeamMember.text = Html.fromHtml(getString(R.string.add_them_here))
        txtAddTeamMember.setOnClickListener {
            val intent = Intent(this, JobInviteTeamMemberActivity::class.java)
            startActivity(intent)
        }

        btnSave = findViewById(R.id.button_save)
        btnSave.setOnClickListener {

            val userManagerList = viewModel.roleAvailableUserListAdapter.getRoleAvailableUserList()
            val userManagerIdList: ArrayList<Int> = arrayListOf()
            for (userManager in userManagerList) {
                if (userManager.tick) {
                    userManagerIdList.add(userManager.user.clientId)
                }
            }

            val role: String  = when (reviewType) {
                Enums.ReviewerType.INTERVIEWER.value -> Enums.ReviewerTypeText.INTERVIEWER.value
                Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> Enums.ReviewerTypeText.INTERVIEWER_ADVANCER.value
                Enums.ReviewerType.OFFER_MANAGER.value -> Enums.ReviewerTypeText.OFFER_MANAGER.value
                else -> ""
            }

            viewModel.addUserRoleToProcessSetting(processId, role, userManagerIdList)
        }
    }

    private fun initShortlistReviewerText() {
        txtReviewType = findViewById(R.id.text_review_type)
        when (reviewType) {
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
                R.plurals.interviewer,
                1, 1
            )
        }
    }
}
