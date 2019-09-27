package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hidden.client.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Get the widgets
        val textForgotPassword = findViewById<TextView>(R.id.text_forgot_password)
        var label_not_a_member = findViewById<TextView>(R.id.text_not_a_member)
        var button_signin = findViewById<Button>(R.id.button_signin)

        // Set a click listener
        textForgotPassword.setOnClickListener(this);
        label_not_a_member.setOnClickListener(this);
        button_signin.setOnClickListener(this);
    }

    override fun onClick(v: View?) {

        val id = v!!.id

        when(id){
            R.id.text_forgot_password -> {
                val intent = Intent(applicationContext, ForgetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.text_not_a_member ->{
                val intent = Intent(applicationContext, SignUpWithInviteCodeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button_signin -> {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
