package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView

class HCJobDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var jobId: String
    private lateinit var companyId: String

    private lateinit var btnBackToYourJob: ImageButton
    private lateinit var layoutShowCompanyDetail: LinearLayout

    private lateinit var imgJobLogo: CircleImageView
    private lateinit var txtJobTitle: TextView
    private lateinit var txtJobCompany: TextView
    private lateinit var txtJobSalary: TextView
    private lateinit var txtJobLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job_detail)

        /***
         * Click Listener
         */
        btnBackToYourJob = findViewById(R.id.button_back_to_your_job)
        btnBackToYourJob.setOnClickListener(this)

        layoutShowCompanyDetail = findViewById(R.id.layout_show_company_detail)
        layoutShowCompanyDetail.setOnClickListener(this)

        /***
         * Init View
         */
        imgJobLogo = findViewById(R.id.img_job_logo)
        txtJobTitle = findViewById(R.id.text_job_title)
        txtJobCompany = findViewById(R.id.text_job_company)
        txtJobSalary = findViewById(R.id.text_job_salary)
        txtJobLocation = findViewById(R.id.text_job_location)

        /***
         * Get JobDetail
         */
        jobId = intent.getStringExtra("jobId")
        HCGlobal.getInstance().log(jobId)
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
