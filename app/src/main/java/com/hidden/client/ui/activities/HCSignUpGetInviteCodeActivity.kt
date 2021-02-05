package com.hidden.client.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.R

class HCSignUpGetInviteCodeActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_get_invite_code)

        val buttonGetStarted = findViewById<Button>(R.id.button_email_team)
        buttonGetStarted.setOnClickListener(this)

        val imageBack = findViewById<ImageButton>(R.id.image_back)
        imageBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.image_back -> {
                finish()
            }

            R.id.button_email_team -> {
                val intent = Intent(Intent.ACTION_VIEW)
                val data: Uri = Uri.parse(
                    "mailto:"
                            + "xyz@abc.com"
                            + "?subject=" + "" + "&body=" + ""
                )
                intent.data = data
                startActivity(intent)
            }
        }
    }
}