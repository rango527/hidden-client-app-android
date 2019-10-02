package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.hidden.client.R

class HcYourJobDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBackToYourJob: ImageButton
    private lateinit var layoutShowCompanyDetail: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job_detail)

        btnBackToYourJob = findViewById(R.id.button_back_to_your_job)
        btnBackToYourJob.setOnClickListener(this)

        layoutShowCompanyDetail = findViewById(R.id.layout_show_company_detail)
        layoutShowCompanyDetail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_your_job -> {
                finish()
            }
            R.id.layout_show_company_detail -> {
                val intent = Intent(applicationContext, HCCompanyDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
