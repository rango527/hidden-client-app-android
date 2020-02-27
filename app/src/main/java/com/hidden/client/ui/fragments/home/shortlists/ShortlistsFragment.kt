package com.hidden.client.ui.fragments.home.shortlists

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.*
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.adapters.ShortlistViewPagerAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ShortlistListVM
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.viewpagerindicator.CirclePageIndicator

class ShortlistsFragment : Fragment(), View.OnClickListener {

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

    // Filter Layout
    private lateinit var layoutFilterContainer: LinearLayout
    private lateinit var layoutFilterPanel: ConstraintLayout
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

        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!))
            .get(ShortlistListVM::class.java)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { visibility ->
            if (visibility) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
                swipeContainer.isRefreshing = false
            }
        })

        viewModel.shortlist.observe(this, Observer { shortlist ->

            Glide.with(context).load(shortlist.clientUrl).into(imgClientPhoto)
            txtClientName.text = resources.getString(R.string.hello_user, AppPreferences.myFullName)
        })

        viewModel.candidateList.observe(this, Observer { candidateVMList ->

            this.candidateVMList = candidateVMList
            HCGlobal.getInstance().shortlistCandidateVMList = candidateVMList

            txtNewProfileCount.text = resources.getQuantityString(
                R.plurals.shortlists_profile_count,
                candidateVMList.size,
                candidateVMList.size
            )

            if (candidateVMList.isEmpty()) {
                layoutViewPager.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE

            } else {
                initViewPager()

                layoutViewPager.visibility = View.VISIBLE
                layoutEmpty.visibility = View.GONE

                layoutBackground.setBackgroundResource(
                    resources.getIdentifier(
                        candidateVMList[0].getShortlistCandidate().avatarColor,
                        "drawable",
                        context!!.packageName
                    )
                )
            }
        })

        return root
    }

    private fun initUI(root: View) {
        imgClientPhoto = root.findViewById(R.id.image_client_photo)
        txtClientName = root.findViewById(R.id.text_hello)
        txtNewProfileCount = root.findViewById(R.id.text_new_profile_count)

        btnRefresh = root.findViewById(R.id.button_refresh)
        btnRefresh.setOnClickListener(this)

        // Layout Filter
        layoutFilterContainer = root.findViewById(R.id.layout_filter_container)
        layoutFilterPanel = root.findViewById(R.id.layout_filter_panel)
        imgOpenFilter = root.findViewById(R.id.img_open_filter_layout)
        imgCloseFilter = root.findViewById(R.id.img_close_filter_layout)
        imgOpenFilter.setOnClickListener(this)
        imgCloseFilter.setOnClickListener(this)

        // Layout container
        layoutEmpty = root.findViewById(R.id.layout_empty_shortlists)
        layoutViewPager = root.findViewById(R.id.layout_has_shortlists)

        swipeContainer = root.findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener {
            viewModel.loadShortlistList(false)
        }

        // View Pager
        viewPagerShortlist = root.findViewById(R.id.viewpager_shortlist)
        indicator = root.findViewById(R.id.indicator)

        layoutBackground = root.findViewById(R.id.layout_background)
    }

    private fun initViewPager() {

        viewPagerShortlist.pageMargin = 30

        pageAdapter = ShortlistViewPagerAdapter(
            activity!!.applicationContext,
            candidateVMList
        )
        viewPagerShortlist.adapter = pageAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerShortlist)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

        if (candidateVMList.size > HCGlobal.getInstance().currentIndex) {
            indicator.setCurrentItem(HCGlobal.getInstance().currentIndex)
            if (candidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().avatarColor.safeValue() != "")
                layoutBackground.setBackgroundResource(
                    Utility.getResourceByName(
                        context!!,
                        Enums.Resource.DRAWABLE.value,
                        candidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().avatarColor
                    )
                )
            else {
                layoutBackground.setBackgroundResource(R.drawable.blue)
            }
        }

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                if (candidateVMList[position].getShortlistCandidate().avatarColor.safeValue() != "") {
                    layoutBackground.setBackgroundResource(
                        Utility.getResourceByName(
                            context!!,
                            Enums.Resource.DRAWABLE.value,
                            candidateVMList[position].getShortlistCandidate().avatarColor
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
                layoutFilterContainer.visibility = View.VISIBLE

                // slide-up animation
                val slideUp = AnimationUtils.loadAnimation(activity, R.anim.anim_slide_in_top)

                layoutFilterPanel.visibility = View.VISIBLE
                layoutFilterPanel.startAnimation(slideUp)

                (activity as HomeActivity).toggleMask()
            }
            R.id.img_close_filter_layout -> {

                // slide-down animation
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
    }
}