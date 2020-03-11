package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.datamodels.HCResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.adapters.FeedbackQuestionViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.FeedbackVM
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    private var feedbackId: Int = 0;
    private var isApprove: Boolean = true
    private var avatarName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        initCloseButton()

        isApprove = intent.getBooleanExtra("isApprove", true);
        processId = intent.getIntExtra("processId", 0)
        avatarName = intent.getStringExtra("avatarName").safeValue()

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

        // Observing for jumping HomeActivity -> Shortlist after add role success
        feedbackViewModel.navigateToShortlist.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        })

        initUI()

        // processId = 430, feedbackId = 1044 for testing
        feedbackViewModel.loadFeedback(430, 1044)
        feedbackViewModel.feedback.observe(this, Observer { feedback ->
            initViewPager(feedback);
        })
    }

    private fun initViewPager(feedback: FeedbackEntity) {
        viewPagerFeedback.pageMargin = 30

        pageAdapter = FeedbackQuestionViewPagerAdapter(
            this,
            feedback.getQuestionList(),
            isApprove
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
            layoutBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen_1))
            txtFeedback.text = getString(R.string.feedback_notice, avatarName)
            txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)
        } else {
            layoutBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed_1))
            txtFeedback.text = getString(R.string.feedback_notice3, avatarName)
            txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)
        }
    }

    fun submitFeedback(questionList: List<FeedbackQuestionEntity>, comment: String) {
        val vote: String = if (isApprove) Enums.VoteType.APPROVE.value else Enums.VoteType.REJECT.value
        val answers = JSONObject()
        for (question in questionList) {
            val questionId = question.id
            val score = question.score

            answers.put(questionId.toString(), score)
        }
        feedbackViewModel.submitFeedback(processId, vote, answers, comment)
    }
}
