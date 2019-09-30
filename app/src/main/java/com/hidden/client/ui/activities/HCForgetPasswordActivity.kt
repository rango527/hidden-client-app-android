package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.hidden.client.R

class HCForgetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        // Get the widgets
        val buttonBackToLogin = findViewById<ImageButton>(R.id.button_backToLogin)

        // Set a click listener
        buttonBackToLogin.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when(id){
            R.id.button_backToLogin->{
                val intent = Intent(applicationContext, HCLoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
