package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hidden.client.R

class HCYourJobActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBackToDashboard: ImageButton
    private lateinit var imgShowJobDetails :ImageView
    private lateinit var layoutYourJob: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job)

        btnBackToDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackToDashboard.setOnClickListener(this)

        imgShowJobDetails = findViewById(R.id.img_show_job_detail)
        imgShowJobDetails.setOnClickListener(this)

        layoutYourJob = findViewById(R.id.layout_your_job)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_dashboard -> {
                finish()
            }
            R.id.img_show_job_detail -> {
                val intent = Intent(applicationContext, HcYourJobDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
