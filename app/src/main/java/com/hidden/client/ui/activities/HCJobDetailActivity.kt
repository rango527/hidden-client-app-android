package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCJobDetailResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCJobDetailTile
import com.hidden.client.ui.adapters.HCCandidateAdapter
import com.hidden.client.ui.adapters.HCJobDetailTileAdapter
import com.hidden.client.ui.viewmodels.HCCandidateViewModel
import com.hidden.client.ui.viewmodels.HCJobDetailTileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCJobDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var jobId: String
    private lateinit var companyId: String

    private lateinit var btnBackToYourJob: ImageButton
    private lateinit var layoutShowCompanyDetail: LinearLayout

    private lateinit var imgCompany: CircleImageView
    private lateinit var imgCompany2: CircleImageView
    private lateinit var imgJob: ImageView
    private lateinit var txtJobTitle: TextView
    private lateinit var txtJobCompany: TextView
    private lateinit var txtJobSalary: TextView
    private lateinit var txtJobLocation: TextView
    private lateinit var txtHiddenSays: TextView
    private lateinit var txtViewCompanyProfile: TextView

    private lateinit var rvJobDetailTile: RecyclerView
    private lateinit var jobDetailTileViewModel: HCJobDetailTileViewModel
    private lateinit var jobDetailTileAdapter: HCJobDetailTileAdapter

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
        imgJob = findViewById(R.id.img_job)
        imgCompany = findViewById(R.id.img_company)
        imgCompany2 = findViewById(R.id.img_company2)
        txtJobTitle = findViewById(R.id.text_job_title)
        txtJobCompany = findViewById(R.id.text_job_company)
        txtJobSalary = findViewById(R.id.text_job_salary)
        txtJobLocation = findViewById(R.id.text_job_location)
        txtHiddenSays = findViewById(R.id.text_hidden_says)
        txtViewCompanyProfile = findViewById(R.id.text_view_company_profile)

        // Job Detail Tile RecyclerView
        rvJobDetailTile = findViewById(R.id.recyclerview_job_detail_tile)
        jobDetailTileViewModel = ViewModelProviders.of(this).get(HCJobDetailTileViewModel::class.java)
        jobDetailTileViewModel.getJobDetailTileList().observe(this, Observer {jobDetailTileViewModels->
            jobDetailTileAdapter = HCJobDetailTileAdapter(applicationContext, jobDetailTileViewModels)
            rvJobDetailTile.layoutManager = LinearLayoutManager(applicationContext)
            rvJobDetailTile.setHasFixedSize(true)

            rvJobDetailTile.adapter = jobDetailTileAdapter
        })

        /***
         * Get JobDetail
         */
        jobId = intent.getStringExtra("jobId")

        // Fetch JobDetail API
        RetrofitClient.instance.getJobDetail(HCGlobal.getInstance().myInfo.getBearerToken(), jobId)
            .enqueue(object: Callback<HCJobDetailResponse> {
                override fun onFailure(call: Call<HCJobDetailResponse>, t: Throwable) {
                    Toast.makeText(this@HCJobDetailActivity, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCJobDetailResponse>,
                    response: Response<HCJobDetailResponse>
                ) {
                    if (response.isSuccessful) {

                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgCompany)
                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgCompany2)
                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.job_cover_image_asset__cloudinary_url).into(imgJob)

                        txtJobTitle.text = response.body()!!.job__title
                        txtJobCompany.text = response.body()!!.company__name
                        txtJobSalary.text = String.format(resources.getString(R.string.salary_range)
                            , response.body()!!.job__salary_from, response.body()!!.job__salary_to)
                        txtJobLocation.text = """   ${response.body()!!.job_city__name}"""
                        txtHiddenSays.text = response.body()!!.company__hidden_says
                        txtViewCompanyProfile.text = String.format(resources.getString(R.string.view_company_profile)
                            , response.body()!!.company__name)

                        var jobDetailTileList: ArrayList<HCJobDetailTile> = arrayListOf()
                        for (detail_tile in response.body()!!.job__tiles) {
                            val jobDetailTile: HCJobDetailTile = HCJobDetailTile()
                            jobDetailTile.setJobDetailTitleId(detail_tile.tile__tile_id)
                            jobDetailTile.setJobDetailTileTitle(detail_tile.tile__title)
                            jobDetailTile.setJobDetailTileContent(detail_tile.tile__content)
                            jobDetailTile.setJobDetailTileType(detail_tile.tile__type)
                            jobDetailTile.setJobDetailTileSortOrder(detail_tile.tile__sort_order)
                            jobDetailTile.setJobDetailTileImg(detail_tile.tile_asset__cloudinary_url)

                            jobDetailTileList.add(jobDetailTile)
                        }

                        jobDetailTileViewModel.setJobDetailTileList(jobDetailTileList)

                    } else {
                        Toast.makeText(this@HCJobDetailActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
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
