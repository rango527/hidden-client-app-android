package com.hidden.client.ui.activities.process

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.hidden.client.R

class HSJumpStageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBackDashboard: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jump_stage)

        btnBackDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackDashboard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_dashboard -> {
                finish()
            }
        }
    }
}
