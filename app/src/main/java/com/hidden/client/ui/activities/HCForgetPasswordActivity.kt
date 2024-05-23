package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_login.*

class HCForgetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: LoginVM
    private lateinit var progressDlg: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        // Get the widgets
        val buttonBackToLogin = findViewById<ImageButton>(R.id.button_backToLogin)
        buttonBackToLogin.setOnClickListener(this)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val btnResetPassword = findViewById<Button>(R.id.button)
        btnResetPassword.setOnClickListener(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        editEmail.doAfterTextChanged {
                text -> viewModel.resetEmail = text
        }

        progressDlg = HCDialog.KProgressDialog(this)

        viewModel.isResetFormValid.observe(this, Observer { valid ->
            btnResetPassword.isEnabled = valid ?: false
        })

        viewModel.loadingResetPasswordVisibility.observe(this, Observer { show ->
            if (show)
                progressDlg.show()
            else
                progressDlg.dismiss()
        })

        viewModel.navigateSendMessageHome.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, HCSentEmailActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.button_backToLogin->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button -> {
                viewModel.resetPassword()
            }
        }
    }
}
