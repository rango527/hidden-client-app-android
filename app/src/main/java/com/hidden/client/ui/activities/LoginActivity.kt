package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.kaopiz.kprogresshud.KProgressHUD

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: LoginVM
    private lateinit var progressDlg: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init View
        val txtForgotPassword: TextView = findViewById(R.id.text_forgot_password)
        val lblNotMember: TextView = findViewById(R.id.text_not_a_member)
        val btnSignIn: Button = findViewById(R.id.button_signin)
        val editEmail: EditText = findViewById(R.id.edit_email)
        val editPassword: EditText = findViewById(R.id.edit_password)

        // Set OnClick Listener
        txtForgotPassword.setOnClickListener(this)
        lblNotMember.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)

        // Init ViewModel
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        editEmail.doAfterTextChanged { text -> viewModel.email = text }
        editPassword.doAfterTextChanged { text -> viewModel.password = text }

        viewModel.isFormValid.observe(this, Observer { valid ->
            btnSignIn.isEnabled = valid ?: false
        })

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        // Observing for jumping HomeActivity after login success
        viewModel.navigateToHome.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.text_forgot_password -> {
                val intent = Intent(applicationContext, HCForgetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.text_not_a_member -> {
                val intent = Intent(applicationContext, HCSignUpWithInviteCodeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button_signin -> {
                viewModel.authLogin()
            }
        }
    }
}
