package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.SignUpVM
import com.kaopiz.kprogresshud.KProgressHUD

class HCSignUpWithInviteCodeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var progressDlg: KProgressHUD
    private lateinit var viewModel: SignUpVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_with_invite_code)

        // Get the widgets
        val textAlreadyMember = findViewById<TextView>(R.id.text_already_a_member)
        val textNotHaveCode = findViewById<TextView>(R.id.text_not_have_code)
        val buttonGetStarted = findViewById<Button>(R.id.button_get_start)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editPassword = findViewById<EditText>(R.id.edit_password)
        val inviteCode = findViewById<EditText>(R.id.edit_invite_code)

        // Set a click listener
        textAlreadyMember.setOnClickListener(this)
        textNotHaveCode.setOnClickListener(this)
        buttonGetStarted.setOnClickListener(this)

        // KProgressHUD
        progressDlg = HCDialog.KProgressDialog(this)

        // Init ViewModel
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(SignUpVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        editEmail.doAfterTextChanged { text -> viewModel.email = text }
        editPassword.doAfterTextChanged { text -> viewModel.password = text }
        inviteCode.doAfterTextChanged { text -> viewModel.code = text }

        viewModel.isFormValid.observe(this, Observer { valid ->
            buttonGetStarted.isEnabled = valid ?: false
        })
    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.text_already_a_member->{
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.text_not_have_code->{
//                val intent = Intent(applicationContext, HCSignUpActivity::class.java)
//                startActivity(intent)
//                finish()
            }
            R.id.button_get_start->{
                val intent = Intent(applicationContext, HCSignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
