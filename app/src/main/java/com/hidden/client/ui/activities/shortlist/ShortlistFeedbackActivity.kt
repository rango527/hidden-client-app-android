package com.hidden.client.ui.activities.shortlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ShortlistFeedbackEntity
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.adapters.ShortlistFeedbackViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ShortlistFeedbackVM
import com.hidden.client.ui.viewmodels.main.ShortlistReviewerViewVM
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator

class ShortlistFeedbackActivity : BaseActivity() {

    private var processId: Int = 0


    private lateinit var imgClose: ImageView

    private lateinit var btnInterviewerFeedbackStatus: Button

    private lateinit var layoutViewPager: LinearLayout

    // Viewpager for Shortlist Feedback Sliding
    private lateinit var viewPagerShortlistFeedback: ViewPager
    private lateinit var pageAdapter: ShortlistFeedbackViewPagerAdapter

    // Swipe Container
    private lateinit var swipeContainer: HorizontalSwipeRefreshLayout

    // View Pager Indicator
    private lateinit var indicator: CirclePageIndicator

    // ViewModel
    private lateinit var ShortlistFeedbackViewModel: ShortlistFeedbackVM

    private var shortlistReviewerViewVMList: List<ShortlistReviewerViewVM> = listOf()

    private lateinit var progressDlg: KProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortlist_feedback)

        processId = intent.getIntExtra("processId", 0)

        initUI()

        progressDlg = HCDialog.KProgressDialog(this)

        ShortlistFeedbackViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ShortlistFeedbackVM::class.java)

        ShortlistFeedbackViewModel.loadShortlistFeedback(processId)

        ShortlistFeedbackViewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
            }
        })

        ShortlistFeedbackViewModel.shortlistReviewerViewVMList.observe(this, Observer { shortlistReviewerViewVM ->
            initViewPager(shortlistReviewerViewVM)
        })

        ShortlistFeedbackViewModel.shortlistFeedback.observe(this, Observer { shortlistFeedback ->
            setUI(shortlistFeedback)
        })

    }

    private fun initUI() {

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }

        btnInterviewerFeedbackStatus = findViewById(R.id.button_interviewer_feedback_status)

        layoutViewPager = findViewById(R.id.layout_has_shortlists)

        swipeContainer = findViewById(R.id.swipe_container)

        // View Pager
        viewPagerShortlistFeedback = findViewById(R.id.viewpager_interviewer)
        indicator = findViewById(R.id.indicator)

    }

    private fun setUI(shortlistFeedback: ShortlistFeedbackEntity) {

        when {
            shortlistFeedback.voteOutcome.safeValue() == Enums.VoteType.APPROVE.value -> {
                btnInterviewerFeedbackStatus.text = getString(R.string.progressing)
                btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
            }
            shortlistFeedback.voteOutcome.safeValue() == Enums.VoteType.REJECT.value -> {
                btnInterviewerFeedbackStatus.text = getString(R.string.rejected)
                btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
            }
            else -> {
                btnInterviewerFeedbackStatus.text = getString(R.string.decision_pending)
                btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
            }
        }

        btnInterviewerFeedbackStatus.visibility = View.VISIBLE

    }

    private fun initViewPager(shortlistReviewerViewVM: List<ShortlistReviewerViewVM>) {

        shortlistReviewerViewVMList = shortlistReviewerViewVM

        viewPagerShortlistFeedback.pageMargin = 20

        pageAdapter = ShortlistFeedbackViewPagerAdapter(
            this,
            shortlistReviewerViewVMList
        )

        viewPagerShortlistFeedback.adapter = pageAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerShortlistFeedback)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density
    }

}
