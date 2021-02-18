package com.hidden.client.ui.activities.process

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.gson.JsonObject
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.adapters.GiveFeedbackQuestionViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.GiveFeedbackVM
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator
import okhttp3.MediaType
import okhttp3.RequestBody

class HSGiveFeedbackActivity : BaseActivity() {

    private lateinit var giveFeedbackViewModel: GiveFeedbackVM

    private lateinit var layoutBackground: ConstraintLayout
    private lateinit var txtFeedback: TextView
    private lateinit var txtFeedback2: TextView

    private lateinit var progressDlg: KProgressHUD

    private lateinit var viewPagerFeedback: ViewPager
    private lateinit var pageAdapter: GiveFeedbackQuestionViewPagerAdapter
    private lateinit var indicator: CirclePageIndicator

    private var processId: Int = 0
    private var feedbackId: Int = 0
    private var isApprove: Boolean = true
    private var avatarName: String = ""
    private var processFeedback: Boolean = false
    private var nextStep: String = ""

    private var candidateName: String = ""
    private var candidateAvatar: String = ""
    private var candidateJob: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_feedback)

        initCloseButton()

        isApprove = intent.getBooleanExtra("isApprove", true)
        processId = intent.getIntExtra("processId", 1)
        avatarName = intent.getStringExtra("avatarName").safeValue()
        processFeedback = intent.getBooleanExtra("processFeedback", false)
        nextStep = intent.getStringExtra("next_step").safeValue()

        if (avatarName == "") {
            avatarName = HCGlobal.getInstance().currentAvatarName
        }

        candidateName = intent.getStringExtra("candidateName").safeValue()

        candidateAvatar = intent.getStringExtra("candidateAvatar").safeValue()
        candidateJob = intent.getStringExtra("candidateJob").safeValue()

        giveFeedbackViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(GiveFeedbackVM::class.java)

        progressDlg = HCDialog.KProgressDialog(this)
        giveFeedbackViewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        // Observing for jumping HomeActivity -> Shortlist after add role success
        giveFeedbackViewModel.navigateToFeedbackDone.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, ProcessActivity::class.java)
                intent.putExtra("processId", processId)
                intent.putExtra("cashMode", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingVTransitionEnter()
                finish()
            }
        })

        initUI()

        feedbackId = HCGlobal.getInstance().currentFeedbackId

        if (nextStep != "") {
            val body: JsonObject = JsonObject()
            body.addProperty("next_step", nextStep)
            giveFeedbackViewModel.nextStep(processId, RequestBody.create(MediaType.parse("application/json"), body.toString()))
        }

        giveFeedbackViewModel.feedbackId = this.feedbackId

        giveFeedbackViewModel.loadGiveFeedback(processId)
        giveFeedbackViewModel.loadTimeline(processId)

        giveFeedbackViewModel.feedback.observe(this, Observer { feedback ->
            initViewPager(feedback)
        })
    }

    private fun initViewPager(feedback: FeedbackEntity) {
        viewPagerFeedback.pageMargin = 30
        // disable scrolling on a viewpager
        viewPagerFeedback.beginFakeDrag()

        pageAdapter = GiveFeedbackQuestionViewPagerAdapter(
            this,
            feedback.getQuestionList(),
            isApprove,
            viewPagerFeedback
        )
        viewPagerFeedback.adapter = pageAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerFeedback)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density
    }

    private fun initUI() {
        viewPagerFeedback = findViewById(R.id.viewpager_give_feedback)
        indicator = findViewById(R.id.give_feedback_indicator)

        layoutBackground = findViewById(R.id.layout_give_feedback_background)
        txtFeedback = findViewById(R.id.text_give_feedback)
        txtFeedback2 = findViewById(R.id.text_give_feedback_2)
//        imgThumbUp = findViewById(R.id.image_thumb_up)

        layoutBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack_1))
        txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)

        if (processFeedback) {
            txtFeedback.text = getString(R.string.feedback_notice1, avatarName)
        } else if (isApprove) {
            txtFeedback.text = getString(R.string.feedback_notice, avatarName)
//            imgThumbUp.setImageResource(R.drawable.thumb_up_large)
        } else {
            txtFeedback.text = getString(R.string.feedback_notice3, avatarName)
//            imgThumbUp.setImageResource(R.drawable.thumb_down_large)
        }
    }

    fun submitFeedback(questionList: List<FeedbackQuestionEntity>, comment: String) {
        val answers = JsonObject()
        for (question in questionList) {
            val questionId = question.id
            val score = question.score

            answers.addProperty(questionId.toString(), score.toString())
        }

        val body: JsonObject = JsonObject()
        body.add("answers", answers)
        body.addProperty("comment", comment)

        giveFeedbackViewModel.submitFeedback(processId, feedbackId, RequestBody.create(MediaType.parse("application/json"), body.toString()))
    }
}
