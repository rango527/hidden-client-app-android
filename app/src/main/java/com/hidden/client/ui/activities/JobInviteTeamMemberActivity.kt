package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobInviteTeamMemberVM
import com.kaopiz.kprogresshud.KProgressHUD

class JobInviteTeamMemberActivity : BaseActivity() {

    private lateinit var progressDlg: KProgressHUD
    private lateinit var viewModel: JobInviteTeamMemberVM

    private lateinit var imgClose: ImageView

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var switchUserManager: Switch

    private lateinit var btnSendInvite: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_invite_team_member)

        HCGlobal.getInstance().currentActivity = this

        // KProgressHUD
        progressDlg = HCDialog.KProgressDialog(this)

        // Init ViewModel
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(JobInviteTeamMemberVM::class.java)

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }

        editFirstName =  findViewById(R.id.edit_first_name)
        editLastName = findViewById(R.id.edit_last_name)
        editEmail = findViewById(R.id.edit_email)
        switchUserManager = findViewById(R.id.switch_user_manager)

        btnSendInvite = findViewById(R.id.button_send_invite)

        editFirstName.doAfterTextChanged { text -> viewModel.firstName = text }
        editLastName.doAfterTextChanged { text -> viewModel.lastName = text }
        editEmail.doAfterTextChanged { text -> viewModel.email = text }

        // Observing for jumping HomeActivity after login success
        viewModel.navigateToAddUserRole.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                HToast.show(this, "Invite Sent", HToast.TOAST_SUCCESS)
                finish()
            }
        })

        viewModel.isFormValid.observe(this, Observer { valid ->
            btnSendInvite.isEnabled = valid ?: true
        })

        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show)
                progressDlg.show()
            else
                progressDlg.dismiss()
        })

        btnSendInvite.setOnClickListener {
            viewModel.isUserManager = switchUserManager.isChecked
            viewModel.inviteTeamMember()
        }
    }
}
