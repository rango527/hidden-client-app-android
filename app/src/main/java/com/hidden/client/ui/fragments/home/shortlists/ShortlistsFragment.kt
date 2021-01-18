package com.hidden.client.ui.fragments.home.shortlists

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.*
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.custom.ShortlistJob
import com.hidden.client.ui.activities.ConsentActivity
import com.hidden.client.ui.activities.ConsentPrivacyActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.adapters.ShortlistJobFilterAdapter
import com.hidden.client.ui.adapters.ShortlistViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ShortlistListVM
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator

class ShortlistsFragment(private val mContext: Context, private val cashMode: Boolean) : Fragment(), View.OnClickListener {
    private lateinit var imgClientPhoto: ImageView
    private lateinit var txtClientName: TextView
    private lateinit var txtNewProfileCount: TextView
    private lateinit var btnRefresh: Button
    private lateinit var layoutBackground: ConstraintLayout

    // Layout: Empty & ViewPager
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var layoutViewPager: LinearLayout

    // Viewpager for Profile Sliding
    private lateinit var viewPagerShortlist: ViewPager
    private lateinit var pageAdapter: ShortlistViewPagerAdapter
    private var candidateVMList: List<ShortlistViewVM> = listOf()
    private var filteredCandidateVMList: List<ShortlistViewVM> = listOf()

    // Filter Layout
    private lateinit var layoutFilterContainer: LinearLayout
    private lateinit var layoutFilterPanel: ConstraintLayout
    private lateinit var textAllJob: TextView
    private lateinit var shortlistFilterJob: RecyclerView
    private lateinit var imgOpenFilter: ImageView
    private lateinit var imgCloseFilter: ImageView

    // Swipe Container
    private lateinit var swipeContainer: HorizontalSwipeRefreshLayout

    // View Pager Indicator
    private lateinit var indicator: CirclePageIndicator

    // ViewModel
    private lateinit var viewModel: ShortlistListVM
    private lateinit var progressDlg: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_shortlists, container, false)

        initUI(root)

        val imageClientPhoto: de.hdodenhof.circleimageview.CircleImageView = root.findViewById(R.id.image_client_photo)
        val textHello: TextView = root.findViewById(R.id.text_hello)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!))
            .get(ShortlistListVM::class.java)

        // get consent value
        viewModel.getConsentUpdate()

        viewModel.consentList.observe(this, Observer { consentList ->
            if (consentList.isNotEmpty()) {
                if (consentList[0].type == "terms") {
                    val intent = Intent(context, ConsentActivity::class.java)
                    intent.putExtra("termsNewVersion", consentList[0].newVersion)
                    intent.putExtra("privacyNewVersion", consentList[1].newVersion)

                    activity!!.startActivity(intent)
                } else if (consentList[0].type == "privacy") {
                    val intent = Intent(context, ConsentPrivacyActivity::class.java)
                    intent.putExtra("privacyNewVersion", consentList[0].newVersion)

                    activity!!.startActivity(intent)
                }
            }
        })

        viewModel.loadShortlistList(cashMode)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { visibility ->
            if (visibility) {
                txtClientName.text = "Hello"
                progressDlg.show()
            } else {
                progressDlg.dismiss()
                swipeContainer.isRefreshing = false
            }
        })
        imageClientPhoto.visibility = View.VISIBLE
        textHello.visibility = View.VISIBLE

        viewModel.shortlist.observe(this, Observer { shortlist ->
            Glide.with(context!!).load(shortlist.clientUrl).into(imgClientPhoto)
            txtClientName.text = resources.getString(R.string.hello_user, AppPreferences.myFullName)
        })

        initCandidateVMList()

        return root
    }

     fun initCandidateVMList() {
        viewModel.candidateList.observe(this, Observer { candidateVMList ->
            this.candidateVMList = candidateVMList

            // set job filter
            var jobText = "various"
            val filteredCandidateVMList = candidateVMList.toMutableList()
            for (ShortlistJob in HCGlobal.getInstance().ShortlistJobList) {
                if (ShortlistJob.jobTick) {
                    for (index in candidateVMList.size downTo 1) {
                        if (ShortlistJob.jobId != candidateVMList[index-1].getShortlistCandidate().jobId) {
                            filteredCandidateVMList.removeAt(index-1)
                        }
                    }
                    jobText = ShortlistJob.jobTitle
                    break
                }
            }

            this.filteredCandidateVMList = filteredCandidateVMList
            HCGlobal.getInstance().shortlistCandidateVMList = filteredCandidateVMList

            if (filteredCandidateVMList.size == 0) {
                txtNewProfileCount.text = "Sorry, there are no new profiles in your $jobText shortlists"
            } else {
                txtNewProfileCount.text = resources.getQuantityString(
                    R.plurals.shortlists_profile_count,
                    filteredCandidateVMList.size,
                    filteredCandidateVMList.size,
                    jobText
                )
            }

            if (filteredCandidateVMList.isEmpty()) {
                layoutViewPager.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
            } else {
                initViewPager()

                layoutViewPager.visibility = View.VISIBLE
                layoutEmpty.visibility = View.GONE

                layoutBackground.setBackgroundResource(
                    resources.getIdentifier(
                        filteredCandidateVMList[0].getShortlistCandidate().avatarColor,
                        "drawable",
                        context!!.packageName
                    )
                )
            }
        })
    }

    private fun initUI(root: View) {
        imgClientPhoto = root.findViewById(R.id.image_client_photo)
        txtClientName = root.findViewById(R.id.text_hello)
        txtNewProfileCount = root.findViewById(R.id.text_new_profile_count)

        btnRefresh = root.findViewById(R.id.button_refresh)
        btnRefresh.setOnClickListener(this)

        // Layout Filter
        layoutFilterContainer = root.findViewById(R.id.layout_filter_container)
        textAllJob = root.findViewById(R.id.text_job_all)
        textAllJob.setOnClickListener(this)
        layoutFilterPanel = root.findViewById(R.id.layout_filter_panel)
        shortlistFilterJob = root.findViewById(R.id.listview_job_list)
        imgCloseFilter = root.findViewById(R.id.img_close_filter_layout)
        imgCloseFilter.setOnClickListener(this)
        imgOpenFilter = root.findViewById(R.id.img_open_filter_layout)
        imgOpenFilter.setOnClickListener(this)

        // Layout container
        layoutEmpty = root.findViewById(R.id.layout_empty_shortlists)
        layoutViewPager = root.findViewById(R.id.layout_has_shortlists)

        swipeContainer = root.findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener {
            viewModel.loadShortlistList(false)
        }

        // View Pager
        viewPagerShortlist = root.findViewById(R.id.viewpager_interviewer)
        indicator = root.findViewById(R.id.indicator)

        layoutBackground = root.findViewById(R.id.layout_background)
    }

    private fun initViewPager() {

        viewPagerShortlist.pageMargin = 30

        pageAdapter = ShortlistViewPagerAdapter(
            activity!!.applicationContext,
            filteredCandidateVMList
        )
        viewPagerShortlist.adapter = pageAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerShortlist)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

        if (filteredCandidateVMList.size > HCGlobal.getInstance().currentIndex) {
            indicator.setCurrentItem(HCGlobal.getInstance().currentIndex)
            if (filteredCandidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().avatarColor.safeValue() != "") {
                layoutBackground.setBackgroundResource(
                    Utility.getResourceByName(
                        context!!,
                        Enums.Resource.DRAWABLE.value,
                        filteredCandidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().avatarColor
                    )
                )
            } else {
                layoutBackground.setBackgroundResource(R.drawable.blue)
            }
        }

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                if (filteredCandidateVMList[position].getShortlistCandidate().avatarColor.safeValue() != "") {
                    layoutBackground.setBackgroundResource(
                        Utility.getResourceByName(
                            context!!,
                            Enums.Resource.DRAWABLE.value,
                            filteredCandidateVMList[position].getShortlistCandidate().avatarColor
                        )
                    )
                } else {
                    layoutBackground.setBackgroundResource(R.drawable.blue)
                }
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_refresh -> {
                viewModel.loadShortlistList(false)
            }
            R.id.img_open_filter_layout -> {
                // get all job list
                if (HCGlobal.getInstance().ShortlistJobList.isEmpty()) {
                    HCGlobal.getInstance().ShortlistJobList.clear()

                    for (candidateJson in candidateVMList) {
                        var index = 0
                        if (HCGlobal.getInstance().ShortlistJobList.isNotEmpty()) {
                            for (shortlistJob in HCGlobal.getInstance().ShortlistJobList) {
                                if (candidateJson.getShortlistCandidate().jobId == shortlistJob.jobId) {
                                    break
                                } else {
                                    index += 1
                                }
                            }
                        }
                        if (index == HCGlobal.getInstance().ShortlistJobList.size || HCGlobal.getInstance().ShortlistJobList.isEmpty()) {
                            val jobId = candidateJson.getShortlistCandidate().jobId
                            val jobTitle = candidateJson.getShortlistCandidate().jobTitle
                            val jobCityName = candidateJson.getShortlistCandidate().jobCityName
                            val joblist = ShortlistJob()

                            joblist.jobId = jobId
                            joblist.jobTitle = jobTitle
                            joblist.jobCityName = jobCityName
                            joblist.jobTick = false

                            HCGlobal.getInstance().ShortlistJobList.add(joblist)
                        }
                    }
                }

                layoutFilterContainer.visibility = View.VISIBLE

                // set All job filter text color
                var jobListIndex =0
                for (ShortlistJob in HCGlobal.getInstance().ShortlistJobList) {
                    if (ShortlistJob.jobTick) {
                        textAllJob.setTextColor(Color.parseColor("#8C8C8C"))
                        break
                    } else {
                        jobListIndex += 1
                    }
                }

                if (jobListIndex == HCGlobal.getInstance().ShortlistJobList.size) {
                    textAllJob.setTextColor(Color.parseColor("#E74A5F"))
                }

                // slide-up animation
                val slideUp = AnimationUtils.loadAnimation(activity, R.anim.anim_slide_in_top)

                layoutFilterPanel.visibility = View.VISIBLE
                layoutFilterPanel.startAnimation(slideUp)

                (activity as HomeActivity).toggleMask()
                // show job list
                if (HCGlobal.getInstance().ShortlistJobList.isEmpty()) {
                    shortlistFilterJob.visibility = View.GONE
                } else {
                    shortlistFilterJob.visibility = View.VISIBLE
                    shortlistFilterJob.layoutManager = LinearLayoutManager(mContext)
                    shortlistFilterJob.adapter = ShortlistJobFilterAdapter(this)
                }
            }
            R.id.img_close_filter_layout -> {
                layoutSlideDown()
            }
            R.id.text_job_all -> {
                textAllJob.setTextColor(Color.parseColor("#E74A5F"))
                for (ShortlistJob in HCGlobal.getInstance().ShortlistJobList) {
                    ShortlistJob.jobTick = false
                }

                layoutSlideDown()
                initCandidateVMList()
            }
        }
    }

    fun layoutSlideDown() {
        val slideDown = AnimationUtils.loadAnimation(activity, R.anim.anim_slide_out_top)
        layoutFilterPanel.startAnimation(slideDown)
        layoutFilterPanel.visibility = View.INVISIBLE

        layoutFilterContainer.animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    layoutFilterContainer.visibility = View.GONE
                    (activity as HomeActivity).toggleMask()
                }
            })
    }
}