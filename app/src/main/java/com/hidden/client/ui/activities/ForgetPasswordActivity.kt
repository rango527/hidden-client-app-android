package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.hidden.client.R

class ForgetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        // Get the widgets
        val button_backToLogin = findViewById<ImageButton>(R.id.button_backToLogin)

        // Set a click listener
        button_backToLogin.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when(id){
            R.id.button_backToLogin->{
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
