package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.hidden.client.R

class SignUpWithInviteCodeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_with_invite_code)

        // Get the widgets
        val text_alreadyMember = findViewById<TextView>(R.id.text_already_a_member)
        var text_notHaveCode = findViewById<TextView>(R.id.text_not_have_code)
        var button_getStarted = findViewById<Button>(R.id.button_get_start)

        // Set a click listener
        text_alreadyMember.setOnClickListener(this);
        text_notHaveCode.setOnClickListener(this);
        button_getStarted.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when(id){
            R.id.text_already_a_member->{
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.text_not_have_code->{
                val intent = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button_get_start->{

            }
        }
    }
}
