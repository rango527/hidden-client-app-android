package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.databinding.ProcessDetailBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.animation.TransformAnimation
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.hidden.client.ui.fragments.process.ProcessTimelineFragment
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessDetailVM
import com.kaopiz.kprogresshud.KProgressHUD
import java.lang.Math.round

class ProcessActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ProcessDetailBinding
    private lateinit var viewModel: ProcessDetailVM

    private lateinit var progressDlg: KProgressHUD

    private lateinit var textBtnProcess: TextView
    private lateinit var textBtnMessage: TextView

    private lateinit var layoutTitle: LinearLayout
    private lateinit var imgProcessSetting: ImageView

    private lateinit var buttonBack: ImageButton

    private lateinit var fragmentProcess: FrameLayout

    private var processId: Int = 0
    private var conversationId: Int = 0
    private var jobId: Int = 0
    private var cashMode: Boolean = true

    private lateinit var imgPhoto: ImageView

//    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processId = intent.getIntExtra("processId", 0)
        conversationId = intent.getIntExtra("conversationId", 0)

        jobId = intent.getIntExtra("jobId", 0)
        // put jobid global variable - this variable is using at the job setting
        HCGlobal.getInstance().currentJobId = jobId

        cashMode = intent.getBooleanExtra("cashMode", true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_process)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ProcessDetailVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        imgPhoto = findViewById(R.id.img_photo)

        viewModel.process.observe(this, Observer { process ->
            viewModel.loadTimeline(cashMode)
            Glide.with(this).load(process.candidateAvatar).into(imgPhoto)
        })

        viewModel.timelineList.observe(this, Observer { timelineList ->
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_process,
                    ProcessTimelineFragment(viewModel.process.value!!, timelineList)
                ).commit()
            }
        })

        viewModel.processId = processId
        HCGlobal.getInstance().log(processId.toString())
        viewModel.loadProcessDetail()

        initUI()
    }

    private fun initUI() {
        fragmentProcess = findViewById(R.id.fragment_process)

        // Init View
        layoutTitle = findViewById(R.id.layout_title)

        textBtnProcess = findViewById(R.id.text_process)
        textBtnProcess.setOnClickListener(this)

        textBtnMessage = findViewById(R.id.text_message)
        textBtnMessage.setOnClickListener(this)

        buttonBack = findViewById(R.id.button_back)
        buttonBack.setOnClickListener(this)

        imgProcessSetting = findViewById(R.id.img_process_setting)
        imgProcessSetting.setOnClickListener(this)

//        swipeContainer = findViewById(R.id.swipeContainer)
//        swipeContainer.setOnRefreshListener {
//            swipeContainer.isRefreshing = false
//        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.text_process -> {
                textBtnProcess.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                textBtnMessage.setBackgroundResource(android.R.color.transparent)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                layoutTitle.measure(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                val animation: Animation =
                    TransformAnimation(layoutTitle, layoutTitle.height, layoutTitle.measuredHeight)
                animation.interpolator = AccelerateInterpolator()
                animation.duration = 50

                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val param = layoutTitle.layoutParams as LinearLayout.LayoutParams
                        param.topMargin = resources.getDimension(R.dimen.margin_default).toInt()
                        layoutTitle.layoutParams = param
                    }

                })
                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_process,
                    ProcessTimelineFragment(
                        viewModel.process.value!!,
                        viewModel.timelineList.value!!
                    )
                ).commit()
            }

            R.id.text_message -> {
                textBtnProcess.setBackgroundResource(android.R.color.transparent)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                textBtnMessage.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                val density: Float = applicationContext.resources.displayMetrics.density

                val animation: Animation =
                    TransformAnimation(layoutTitle, layoutTitle.height, round(40 * density))
                animation.interpolator = AccelerateInterpolator()
                animation.duration = 100

                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val param = layoutTitle.layoutParams as LinearLayout.LayoutParams
                        param.topMargin =
                            resources.getDimension(R.dimen.list_row_margin_default).toInt()
                        layoutTitle.layoutParams = param
                    }
                })

                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_process, HCMessageFragment(conversationId)).commit()

//                val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
//                intent.putExtra("conversationId", processId)
//                startActivity(intent)
//                overridePendingVTransitionEnter()

            }
            R.id.button_back -> {
                finish()
            }
            R.id.img_process_setting -> {
                val intent = Intent(this, ProcessSettingActivity::class.java)
                intent.putExtra("processId", processId)
                intent.putExtra("jobId", jobId)
                startActivity(intent)
                overridePendingVTransitionEnter()
            }
        }
    }
}
