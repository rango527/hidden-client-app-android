package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCJobDetailResponse
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCJobActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var jobId: String

    private lateinit var btnBackToDashboard: ImageButton
    private lateinit var imgShowJobDetails :ImageView

    private lateinit var imgBackground: ImageView
    private lateinit var imgJob: CircleImageView
    private lateinit var txtJobTitle: TextView
    private lateinit var txtJobCompany: TextView
    private lateinit var txtJobSalary: TextView
    private lateinit var txtJobLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job)

        /***
         * Click Listener
         */
        btnBackToDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackToDashboard.setOnClickListener(this)

        imgShowJobDetails = findViewById(R.id.img_show_job_detail)
        imgShowJobDetails.setOnClickListener(this)

        /***
         * Init View
         */
        imgBackground = findViewById(R.id.img_background)
        imgJob = findViewById(R.id.img_job)
        txtJobTitle = findViewById(R.id.text_job_title)
        txtJobCompany = findViewById(R.id.text_job_company)
        txtJobSalary = findViewById(R.id.text_job_salary)
        txtJobLocation = findViewById(R.id.text_job_location)

        /***
         * Get Job Detail API
         */
        jobId = intent.getStringExtra("jobId")!!

        // Fetch JobDetail API
        RetrofitClient.instance.getJobDetail(HCGlobal.getInstance().myInfo.getBearerToken(), jobId)
            .enqueue(object: Callback<HCJobDetailResponse> {
                override fun onFailure(call: Call<HCJobDetailResponse>, t: Throwable) {
                    Toast.makeText(this@HCJobActivity, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCJobDetailResponse>,
                    response: Response<HCJobDetailResponse>
                ) {
                    if (response.isSuccessful) {

                        Glide.with(this@HCJobActivity).load(response.body()!!.job_cover_image_asset__cloudinary_url).into(imgBackground)
                        Glide.with(this@HCJobActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgJob)
                        txtJobTitle.text = response.body()!!.job__title
                        txtJobCompany.text = response.body()!!.company__name
                        txtJobSalary.text = String.format(resources.getString(R.string.salary_range)
                            , response.body()!!.job__salary_from, response.body()!!.job__salary_to)
                        txtJobLocation.text = """   ${response.body()!!.job_city__name}"""

                    } else {
                        Toast.makeText(this@HCJobActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back_to_dashboard -> {
                finish()
            }
            R.id.img_show_job_detail -> {
                val intent = Intent(applicationContext, HCJobDetailActivity::class.java)
                intent.putExtra("jobId", jobId)
                startActivity(intent)
            }
        }
    }
}
