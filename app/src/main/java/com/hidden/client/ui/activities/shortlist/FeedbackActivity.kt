package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.gson.JsonObject
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.FeedbackQuestionViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.FeedbackVM
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator
import okhttp3.MediaType
import okhttp3.RequestBody

class FeedbackActivity : BaseActivity() {

    private lateinit var feedbackViewModel: FeedbackVM

    private lateinit var layoutBackground: ConstraintLayout
    private lateinit var txtFeedback: TextView
    private lateinit var txtFeedback2: TextView

    private lateinit var progressDlg: KProgressHUD

    private lateinit var viewPagerFeedback: ViewPager
    private lateinit var pageAdapter: FeedbackQuestionViewPagerAdapter
    private lateinit var indicator: CirclePageIndicator

    private var processId: Int = 0
    private var isApprove: Boolean = true
    private var avatarName: String = ""

    private var candidateName: String = ""
    private var candidateAvatar: String = ""
    private var candidateJob: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        initCloseButton()

        isApprove = intent.getBooleanExtra("isApprove", true)
        processId = intent.getIntExtra("processId", 0)
        avatarName = intent.getStringExtra("avatarName").safeValue()

        candidateName = intent.getStringExtra("candidateName").safeValue()
        candidateAvatar = intent.getStringExtra("candidateAvatar").safeValue()
        candidateJob = intent.getStringExtra("candidateJob").safeValue()

        feedbackViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(FeedbackVM::class.java)

        progressDlg = HCDialog.KProgressDialog(this)
        feedbackViewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        initUI()

        feedbackViewModel.loadTimeline(processId)
        feedbackViewModel.feedbackId.observe(this, Observer { feedbackId ->
            feedbackViewModel.loadFeedback(processId, feedbackId)
        })

        feedbackViewModel.feedback.observe(this, Observer { feedback ->
            initViewPager(feedback)
        })

        // Observing for jumping HomeActivity -> Shortlist after add role success
        feedbackViewModel.statusAfterVote.observe(this, Observer { statusAfterVote ->
            val intent = Intent(this, FeedbackDoneActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("statusAfterVote", statusAfterVote.statusAfterVote)
            intent.putExtra("candidateName", candidateName)
            intent.putExtra("candidateAvatar", candidateAvatar)
            intent.putExtra("candidateJob", candidateJob)
            startActivity(intent)
        })
    }

    private fun initViewPager(feedback: FeedbackEntity) {
        viewPagerFeedback.pageMargin = 30
        // disable scrolling on a viewpager
        viewPagerFeedback.beginFakeDrag()

        pageAdapter = FeedbackQuestionViewPagerAdapter(
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

        viewPagerFeedback = findViewById(R.id.viewpager_feedback)
        indicator = findViewById(R.id.indicator)

        layoutBackground = findViewById(R.id.layout_background)
        txtFeedback = findViewById(R.id.text_feedback_notice)
        txtFeedback2 = findViewById(R.id.text_feedback_notice2)

        if (isApprove) {
            txtFeedback.text = getString(R.string.feedback_notice, avatarName)
            txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)
        } else {
            txtFeedback.text = getString(R.string.feedback_notice3, avatarName)
            txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)
        }
    }

    fun submitFeedback(questionList: List<FeedbackQuestionEntity>, comment: String) {
        val vote: String = if (isApprove) Enums.VoteType.APPROVE.value else Enums.VoteType.REJECT.value
        val answers = JsonObject()
        for (question in questionList) {
            val questionId = question.id
            val score = question.score

            answers.addProperty(questionId.toString(), score)
        }

        val body = JsonObject()
        body.addProperty("vote", vote)
        body.add("answers", answers)
        body.addProperty("comment", comment)
        feedbackViewModel.submitFeedback(processId, RequestBody.create(MediaType.parse("application/json"), body.toString()))
    }
}
