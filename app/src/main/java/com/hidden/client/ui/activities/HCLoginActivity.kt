package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hidden.client.R

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
                val intent = Intent(applicationContext, HCHomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
