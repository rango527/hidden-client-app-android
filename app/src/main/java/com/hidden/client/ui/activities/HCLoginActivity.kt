package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCLoginResponse
import com.hidden.client.helpers.HCGlobal
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCLoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var progressDlg: KProgressHUD;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Get the widgets
        val textForgotPassword = findViewById<TextView>(R.id.text_forgot_password)
        var labelNotMember = findViewById<TextView>(R.id.text_not_a_member)
        var buttonSignIn = findViewById<Button>(R.id.button_signin)

        // Set a click listener
        textForgotPassword.setOnClickListener(this);
        labelNotMember.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

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
