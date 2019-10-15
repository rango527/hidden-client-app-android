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
import com.hidden.client.datamodels.HCClientResponse
import com.hidden.client.helpers.HCGlobal
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCLoginActivity : AppCompatActivity(), View.OnClickListener {

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

                RetrofitClient.instance.clientLogin(email, password)
                    .enqueue(object: Callback<HCClientResponse> {
                        override fun onFailure(call: Call<HCClientResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, "dfjslfhkjsdf", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<HCClientResponse>,
                            response: Response<HCClientResponse>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(applicationContext, response.body()?.full_name, Toast.LENGTH_LONG).show()

                                HCGlobal.getInstance(this@HCLoginActivity).g_client.setId(response.body()!!.id)
                                HCGlobal.getInstance(this@HCLoginActivity).g_client.setFullName(response.body()!!.full_name)
                                HCGlobal.getInstance(this@HCLoginActivity).g_client.setIsAdmin(response.body()!!.is_admin)
                                HCGlobal.getInstance(this@HCLoginActivity).g_client.setStatus(response.body()!!.status)
                                HCGlobal.getInstance(this@HCLoginActivity).g_client.setToken(response.body()!!.token)

                                val intent = Intent(applicationContext, HCHomeActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                HCGlobal.getInstance(this@HCLoginActivity).HCLog(response.errorBody()!!.string())
                                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }
}
