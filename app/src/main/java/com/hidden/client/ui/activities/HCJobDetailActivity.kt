package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.util.Pair
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeListener
import com.hidden.client.R
import com.hidden.client.datamodels.HCJobDetailResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.adapters.HCJobDetailTileAdapter
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCJobDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var jobId: String

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

    private lateinit var swipe: Swipe

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job_detail)

        HCGlobal.getInstance().currentActivity = this

        // Animation
        setupWindowAnimations()

        // Init Swipe
        initSwipe()

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
            jobDetailTileAdapter = HCJobDetailTileAdapter(jobDetailTileViewModels)
            rvJobDetailTile.layoutManager = LinearLayoutManager(applicationContext)
            rvJobDetailTile.setHasFixedSize(true)

            rvJobDetailTile.adapter = jobDetailTileAdapter
        })

        /***
         * Get JobDetail
         */
        jobId = intent.getStringExtra("jobId")!!

        // Fetch JobDetail API
        RetrofitClient.instance.getJobDetail(AppPreferences.apiAccessToken, jobId)
            .enqueue(object: Callback<HCJobDetailResponse> {
                override fun onFailure(call: Call<HCJobDetailResponse>, t: Throwable) {
                    Toast.makeText(this@HCJobDetailActivity, "Failed...", Toast.LENGTH_LONG).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<HCJobDetailResponse>,
                    response: Response<HCJobDetailResponse>
                ) {
                    if (response.isSuccessful) {

                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgCompany)
                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgCompany2)
//                        Glide.with(this@HCJobDetailActivity).load(response.body()!!.job_cover_image_asset__cloudinary_url).into(imgJob)

                        txtJobTitle.text = response.body()!!.job__title
                        txtJobCompany.text = response.body()!!.company__name
                        txtJobSalary.text = String.format(resources.getString(R.string.salary_range)
                            , response.body()!!.job__salary_from, response.body()!!.job__salary_to)
                        txtJobLocation.text = """   ${response.body()!!.job_city__name}"""
                        txtHiddenSays.text = response.body()!!.company__hidden_says
                        txtViewCompanyProfile.text = String.format(resources.getString(R.string.view_company_profile)
                            , response.body()!!.company__name)

                        val jobDetailTileList: ArrayList<HCJobDetailTile> = arrayListOf()
                        for (detail_tile in response.body()!!.job__tiles) {
                            val jobDetailTile = HCJobDetailTile()
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
                backToJobActivity()
            }
            R.id.layout_show_company_detail -> {
                val intent = Intent(applicationContext, HCCompanyDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun backToJobActivity() {
        val intent = Intent(applicationContext, HCJobActivity::class.java)

        val p1 = Pair.create<View, String>(imgJob, getString(R.string.job_cover_transition))
        val p2 = Pair.create<View, String>(imgCompany, getString(R.string.job_logo_transition))

        val pairs = ArrayList<Pair<View, String>>()
        pairs.add(p1)
        pairs.add(p2)
        val pairsArray: Array<Pair<View, String>> = pairs.toTypedArray()

        val transitionActivityOption: ActivityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, *pairsArray)
        intent.putExtra("jobId", jobId)
        startActivity(intent, transitionActivityOption.toBundle())
        finish()
    }

    override fun onBackPressed() {
        backToJobActivity()
    }

    private fun setupWindowAnimations() {
        val slideTransition = ChangeClipBounds()
//        slideTransition.slideEdge = Gravity.BOTTOM
        slideTransition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()

        val changeBoundsTransaction = ChangeBounds()
        changeBoundsTransaction.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()

//        window.enterTransition = slideTransition
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
        window.allowEnterTransitionOverlap = true
        window.allowReturnTransitionOverlap = true
        window.sharedElementEnterTransition = changeBoundsTransaction
        window.sharedElementExitTransition = changeBoundsTransaction
    }

    private fun initSwipe() {
        swipe = Swipe()
        swipe.setListener(object : SwipeListener {
            override fun onSwipingLeft(event: MotionEvent) {
            }

            override fun onSwipedLeft(event: MotionEvent): Boolean {
                return true
            }

            override fun onSwipingRight(event: MotionEvent) {
            }

            override fun onSwipedRight(event: MotionEvent): Boolean {
                return true
            }

            override fun onSwipingUp(event: MotionEvent) {
            }

            override fun onSwipedUp(event: MotionEvent): Boolean {
                return true
            }

            override fun onSwipingDown(event: MotionEvent) {
            }

            override fun onSwipedDown(event: MotionEvent): Boolean {
                backToJobActivity()
                return true
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        swipe.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }
}
