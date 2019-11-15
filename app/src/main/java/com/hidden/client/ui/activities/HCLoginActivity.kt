package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCLoginResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Environment
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCLoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var progressDlg: KProgressHUD;

    private lateinit var viewModel: LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init View
        val txtForgotPassword: TextView    = findViewById(R.id.text_forgot_password)
        val lblNotMember: TextView        = findViewById(R.id.text_not_a_member)
        val btnSignIn: Button            = findViewById(R.id.button_signin)
        val editEmail: EditText             = findViewById(R.id.edit_email)
        val editPassword: EditText          = findViewById(R.id.edit_password)

        // Set OnClick Listener
        txtForgotPassword.setOnClickListener(this);
        lblNotMember.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        // KProgressHUD
        progressDlg = HCDialog.KProgressDialog(this)

        // Init ViewModel
//        viewModel = ViewModelProviders.of(this).get(LoginVM::class.java)
//
//        viewModel.isFormValid.observe(this, Observer { valid ->
//            btnSignIn.isEnabled = valid ?: false
//        })
//
//        viewModel.loadingVisibility.observe(this, Observer { show ->
//            HCGlobal.getInstance().log(show.toString())
//            if (show)
//                progressDlg.show()
//            else
//                progressDlg.dismiss()
//        })
//
//        editEmail.doAfterTextChanged { text -> viewModel.email = text?.toString() ?: "" }
//        editPassword.doAfterTextChanged { text -> viewModel.password = text?.toString() ?: "" }
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when(id){
            R.id.text_forgot_password -> {
                val intent = Intent(applicationContext, HCForgetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.text_not_a_member ->{
                val intent = Intent(applicationContext, HCSignUpWithInviteCodeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button_signin -> {
//                viewModel.authLogin()

                val email = edit_email.text.toString().trim()
                val password = edit_password.text.toString().trim()

                progressDlg = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                progressDlg.show()

                RetrofitClient.instance.clientLogin(email, password)
                    .enqueue(object: Callback<HCLoginResponse> {
                        override fun onFailure(call: Call<HCLoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<HCLoginResponse>,
                            response: Response<HCLoginResponse>
                        ) {

                            progressDlg.dismiss()

                            if (response.isSuccessful) {

                                HCGlobal.getInstance().myInfo.setId(response.body()!!.id)
                                HCGlobal.getInstance().myInfo.setFullName(response.body()!!.full_name)
                                HCGlobal.getInstance().myInfo.setIsAdmin(response.body()!!.is_admin)
                                HCGlobal.getInstance().myInfo.setStatus(response.body()!!.status)
                                HCGlobal.getInstance().myInfo.setToken(response.body()!!.token)

                                AppPreferences.apiAccessToken = response.body()!!.token

                                val intent = Intent(applicationContext, HCHomeActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                HCGlobal.getInstance().log(response.errorBody()!!.string())
                                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }
}
