package com.hidden.client.ui.activities.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.hidden.client.R

class InviteAcceptActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var editPassword: EditText

    private lateinit var btnLetGo:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_accept)

        editPassword = findViewById(R.id.edit_password)

        btnLetGo = findViewById(R.id.btn_let_go)
        btnLetGo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

    }
}
