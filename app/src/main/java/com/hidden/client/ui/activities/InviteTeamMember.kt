package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.hidden.client.R

class InviteTeamMember : AppCompatActivity() {

    private lateinit var imgClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_team_member)

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }
    }
}
