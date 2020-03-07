package com.hidden.client.ui.activities.process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.ProcessTabBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.JobSettingActivity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobAddUserRoleVM
import com.hidden.client.ui.viewmodels.main.ProcessTabVM
import com.kaopiz.kprogresshud.KProgressHUD

class AddInterviewersActivity : BaseActivity() {

    private lateinit var binding: ProcessTabBinding
    private lateinit var viewModel: ProcessTabVM

    private lateinit var imgClose: ImageView
    private lateinit var txtTitle: TextView

    private lateinit var btnSave: Button
    private lateinit var editSearch: EditText

    private lateinit var progressDlg: KProgressHUD

    private var processId: Int = 0
    private var interviewId: Int = 0
    private var candidateName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processId = intent.getIntExtra("processId", 0)
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
        txtTitle.text = String.format(getString(R.string.add_interviewers_to_interview), candidateName)

        btnSave = findViewById(R.id.button_save)
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
        startActivity(intent)
        overridePendingVTransitionEnter()
        finish()
    }
}
