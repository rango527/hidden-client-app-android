package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.databinding.ProcessDetailBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.settings.CandidateDetailActivity
import com.hidden.client.ui.animation.TransformAnimation
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.hidden.client.ui.fragments.process.ProcessTimelineFragment
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.GiveFeedbackVM
import com.hidden.client.ui.viewmodels.main.ProcessDetailVM
import com.kaopiz.kprogresshud.KProgressHUD
import okhttp3.RequestBody
import kotlin.math.roundToInt

class ProcessActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ProcessDetailBinding
    private lateinit var viewModel: ProcessDetailVM

    private lateinit var progressDlg: KProgressHUD
    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var textBtnProcess: TextView
    private lateinit var textBtnMessage: TextView

    private lateinit var layoutTitle: LinearLayout
    private lateinit var imgProcessSetting: ImageView

    private lateinit var buttonBack: ImageButton

    private lateinit var fragmentProcess: FrameLayout

    private var processId: Int = 0
    private var conversationId: Int = 0
    private var jobId: Int = 0
    private var candidateId: Int = 0

    private lateinit var imgPhoto: ImageView
    private lateinit var giveFeedbackViewModel: GiveFeedbackVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processId = intent.getIntExtra("processId", 0)
        conversationId = intent.getIntExtra("conversationId", 0)

        jobId = intent.getIntExtra("jobId", 0)
        candidateId = intent.getIntExtra("candidateId", 0)
        // put jobid global variable - this variable is using at the job setting
        HCGlobal.getInstance().currentJobId = jobId

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
                swipeContainer.isRefreshing = false
            }
        })
        imgPhoto = findViewById(R.id.img_photo)

        viewModel.processId = processId
        HCGlobal.getInstance().log(processId.toString())

        viewModel.loadProcess()

        viewModel.loadProcess.observe(this, Observer { loadProcess ->
            viewModel.loadProcessDetail()
        })

        viewModel.process.observe(this, Observer { process ->
            viewModel.loadTimeline(false)
            Glide.with(this).load(process.candidateAvatar).into(imgPhoto)
        })

        viewModel.timelineList.observe(this, Observer { timelineList ->
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_process,
                    ProcessTimelineFragment(viewModel.process.value!!, viewModel.isInterviewAdvancer.value!!, timelineList)
                ).commit()
            }
        })

        initUI()
    }

    private fun initUI() {
        fragmentProcess = findViewById(R.id.fragment_process)
        // subview
        imgPhoto.setOnClickListener(this)

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

        imgPhoto.setOnClickListener(this)

        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            viewModel.loadProcess()
        }
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
                    TransformAnimation(layoutTitle, layoutTitle.height, layoutTitle.measuredHeight + 22)
                // +23 is layoutTitle and process, message layout distance
                val density: Float = applicationContext.resources.displayMetrics.density

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
//                        param.topMargin = resources.getDimension(R.dimen.margin_default).toInt()
                        imgPhoto.layoutParams.width = (density * 100).roundToInt()
                        imgPhoto.layoutParams.height = (density * 99).roundToInt()
                        layoutTitle.layoutParams = param
                    }

                })

                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_process,
                    ProcessTimelineFragment(
                        viewModel.process.value!!,
                        viewModel.isInterviewAdvancer.value!!,
                        viewModel.timelineList.value!!
                    )
                ).commit()
            }

            R.id.text_message -> {
                swipeContainer.isRefreshing = false
                swipeContainer.isEnabled = false

                textBtnProcess.setBackgroundResource(android.R.color.transparent)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                textBtnMessage.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                val density: Float = applicationContext.resources.displayMetrics.density

//                HCGlobal.getInstance().convertDpToPx(applicationContext, 42)
                val animation: Animation =
                    TransformAnimation(layoutTitle, layoutTitle.height, (42 * density).roundToInt())
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
                        param.bottomMargin =
                            resources.getDimension(R.dimen.list_row_margin_default).toInt()
//                        imgPhoto.setPadding((30 * density).roundToInt(), 0, (30 * density).roundToInt(), (60 * density).roundToInt())
                        imgPhoto.layoutParams.height = (density * 42).roundToInt()
                        imgPhoto.layoutParams.width = (density * 41).roundToInt()

                        layoutTitle.layoutParams = param
                    }
                })

                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_process, HCMessageFragment(conversationId)).commit()
            }

            R.id.button_back -> {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
            }
            R.id.img_process_setting -> {
                val intent = Intent(this, ProcessSettingActivity::class.java)
                intent.putExtra("processId", processId)
                intent.putExtra("jobId", jobId)
                intent.putExtra("cashMode", false)
                startActivity(intent)
                overridePendingVTransitionEnter()
            }
            R.id.img_photo -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, CandidateDetailActivity::class.java)
                intent.putExtra("category_id", candidateId.toString())
                HCGlobal.getInstance().currentActivity.startActivity(intent)
                overridePendingVTransitionEnter()
            }
        }
    }

    fun nudgeFeedback(processId: Int, feedbackId: Int, body: RequestBody) {
        giveFeedbackViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(GiveFeedbackVM::class.java)
        giveFeedbackViewModel.nudgeFeedback(processId, feedbackId, body)
    }
}
