package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.ResetPasswordVM
import com.kaopiz.kprogresshud.KProgressHUD

class NewSetPasswordActivity : BaseActivity(), View.OnClickListener {
    private lateinit var progressDlg: KProgressHUD
    private lateinit var viewModel: ResetPasswordVM
    private lateinit var email: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        val data = intent.data
        email = data?.getQueryParameter("email").toString()
        token = data?.getQueryParameter("token").toString()

        val editNewPassword: EditText = findViewById(R.id.edit_new_password)
        val editOnceMore: EditText = findViewById(R.id.edit_once_more_time)
        val btnSignIn: Button = findViewById(R.id.button_reset_password)
        btnSignIn.setOnClickListener(this)

        // KProgressHUD
        progressDlg = HCDialog.KProgressDialog(this)

        // Init ViewModel
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(ResetPasswordVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        editNewPassword.doAfterTextChanged { text -> viewModel.newPassword = text }
        editOnceMore.doAfterTextChanged { text -> viewModel.newPasswordOnceMore = text }

        viewModel.isNewPasswordValid.observe(this, Observer { valid ->
            btnSignIn.isEnabled = valid ?: false
        })

        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show)
                progressDlg.show()
            else
                progressDlg.dismiss()
        })

        // Observing for jumping HomeActivity after login success
        viewModel.navigateNewPassword.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_reset_password -> {
                viewModel.changeForgottenPassword(email, token)
            }
        }
    }
}
