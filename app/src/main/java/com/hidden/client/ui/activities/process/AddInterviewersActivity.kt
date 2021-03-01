package com.hidden.client.ui.activities.process

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.hidden.client.R
import com.hidden.client.databinding.ProcessTabBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.dialogs.BottomAddInterviewerToInterviewDialog
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessTabVM
import com.kaopiz.kprogresshud.KProgressHUD
import okhttp3.MediaType
import okhttp3.RequestBody

class AddInterviewersActivity : BaseActivity() {

    private lateinit var binding: ProcessTabBinding
    private lateinit var viewModel: ProcessTabVM

    private lateinit var imgClose: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var txtAddTeamMember: TextView

    private lateinit var btnSave: Button
    private lateinit var editSearch: EditText

    private lateinit var progressDlg: KProgressHUD

    private var from: String? = ""
    private var processId: Int = 0
    private var jobId: Int = 0
    private var interviewId: Int = 0
    private var candidateName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        from = intent.getStringExtra("from")
        processId = intent.getIntExtra("processId", 0)
        jobId = intent.getIntExtra("jobId", 0)
        interviewId = intent.getIntExtra("interviewId", 0)
        candidateName = intent.getStringExtra("candidateName")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_interviewers)
        binding.recyclerviewInterviewer.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ProcessTabVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        // Observing for jumping Process Detail Activity after add role success
        viewModel.navigateToProcess.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                backToProcess(false)
            }
        })

        viewModel.loadAvailableInterviewerList(processId, interviewId)

        initUI()
    }

    private fun initUI() {
        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener {
            finish()
        }

        txtTitle = findViewById(R.id.text_title)
        txtTitle.text = String.format(getString(R.string.add_interviewers_to_interview), from, candidateName)

        editSearch = findViewById(R.id.edit_search)
        editSearch.doAfterTextChanged { text -> viewModel.search = text }

        txtAddTeamMember = findViewById(R.id.text_add_team_member)
        txtAddTeamMember.text = Html.fromHtml(getString(R.string.can_not_find_any_interviewers))
        txtAddTeamMember.setOnClickListener {
            BottomAddInterviewerToInterviewDialog.display(supportFragmentManager, processId, jobId);
//            var bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_add_interviewer_to_interview)
//
//            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
//            } else {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//            }
//            val intent = Intent(this, JobInviteTeamMemberActivity::class.java)
//            startActivity(intent)
        }

        btnSave = findViewById(R.id.button_save)
        btnSave.setOnClickListener {

            val interviewerList = viewModel.availableInterviewerListAdapter.getRoleAvailableUserList()
            val interviewerIdList: ArrayList<Int> = arrayListOf()

            val jArray: JsonArray = JsonArray()

            for (interviewer in interviewerList) {
                if (interviewer.tick) {
                    interviewerIdList.add(interviewer.user.clientId)
                    jArray.add(interviewer.user.clientId)
                    HCGlobal.getInstance().log(interviewer.user.clientId.toString())
                }
            }

            val body: JsonObject = JsonObject()

            body.add("client_ids", jArray)

            viewModel.addInterviewersToInterview(processId, interviewId, RequestBody.create(
                MediaType.parse("application/json"), body.toString()))
        }
    }

    fun setSaveButtonEnable() {
        var enable = false;
        for (userManager in viewModel.availableInterviewerListAdapter.getRoleAvailableUserList()) {
            if (userManager.tick) {
                enable = true
                break;
            }
        }
        btnSave.isEnabled = enable
    }

    override fun onBackPressed() {
        backToProcess(true)
    }

    private fun backToProcess(cashMode: Boolean) {
        val intent = Intent(this, ProcessActivity::class.java)
        intent.putExtra("processId", processId)
        intent.putExtra("cashMode", cashMode)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingVTransitionEnter()
        finish()
    }
}
