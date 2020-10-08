package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.util.Log
import android.util.Pair
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeListener
import com.hidden.client.R
import com.hidden.client.datamodels.HCJobDetailResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HCJobActivity : BaseActivity(), View.OnClickListener {

    private lateinit var jobId: String

    private lateinit var btnBackToDashboard: ImageButton
    private lateinit var imgShowJobDetails: ImageView
    private lateinit var imgJobSetting: ImageView

    private lateinit var imgBackground: ImageView
    private lateinit var layoutTransparent: LinearLayout

    private lateinit var imgJob: CircleImageView
    private lateinit var txtJobTitle: TextView
    private lateinit var txtJobCompany: TextView
    private lateinit var txtJobSalary: TextView
    private lateinit var txtJobLocation: TextView

    private lateinit var swipe: Swipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_job)

        HCGlobal.getInstance().currentActivity = this

        /***
         * Click Listener
         */
        btnBackToDashboard = findViewById(R.id.button_back_to_dashboard)
        btnBackToDashboard.setOnClickListener(this)

        imgShowJobDetails = findViewById(R.id.img_show_job_detail)
        imgShowJobDetails.setOnClickListener(this)

        imgJobSetting = findViewById(R.id.img_job_setting)
        imgJobSetting.setOnClickListener(this)

        /***
         * Init View
         */
        imgBackground = findViewById(R.id.img_background)
        layoutTransparent = findViewById(R.id.layout_transparent)

        imgJob = findViewById(R.id.img_job)
        txtJobTitle = findViewById(R.id.text_job_title)
        txtJobCompany = findViewById(R.id.text_job_company)
        txtJobSalary = findViewById(R.id.text_job_salary)
        txtJobLocation = findViewById(R.id.text_job_location)

        // Animation Configuration
        setupWindowAnimations()

        // Init Swipe
        initSwipe()

        /***
         * Get Job Detail API
         */
        jobId = intent.getStringExtra("jobId")!!

        // Fetch JobDetail API
        RetrofitClient.instance.getJobDetail(AppPreferences.apiAccessToken, jobId)
            .enqueue(object : Callback<HCJobDetailResponse> {
                override fun onFailure(call: Call<HCJobDetailResponse>, t: Throwable) {
                    Toast.makeText(this@HCJobActivity, "Failed...", Toast.LENGTH_LONG).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<HCJobDetailResponse>,
                    response: Response<HCJobDetailResponse>
                ) {
                    if (response.isSuccessful) {

                        Glide.with(this@HCJobActivity)
                            .load(response.body()!!.job_cover_image_asset__cloudinary_url)
                            .into(imgBackground)
                        Glide.with(this@HCJobActivity)
                            .load(response.body()!!.company_logo_asset__cloudinary_url).into(imgJob)
                        txtJobTitle.text = response.body()!!.job__title
                        txtJobCompany.text = response.body()!!.company__name
                        txtJobSalary.text = String.format(
                            resources.getString(R.string.salary_range)
                            , response.body()!!.job__salary_from, response.body()!!.job__salary_to
                        )
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
                navigateJobDetailActivity()
            }
            R.id.img_job_setting -> {
                val intent = Intent(this, JobSettingActivity::class.java)
                intent.putExtra("jobId", jobId.toInt())
                startActivity(intent)
                overridePendingVTransitionEnter()
            }
        }
    }

    private fun navigateJobDetailActivity() {
        val intent = Intent(applicationContext, HCJobDetailActivity::class.java)

        val p1 = Pair.create<View, String>(imgJob, getString(R.string.job_logo_transition))
        val p2 = Pair.create<View, String>(imgBackground, getString(R.string.job_cover_transition))

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

    private fun setupWindowAnimations() {
        val slideTransition = ChangeClipBounds()
        slideTransition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()

        val changeBoundsTransaction = ChangeBounds()
        changeBoundsTransaction.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()

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
                navigateJobDetailActivity()
                return true
            }

            override fun onSwipingDown(event: MotionEvent) {
            }

            override fun onSwipedDown(event: MotionEvent): Boolean {
                return true
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        swipe.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }
}
