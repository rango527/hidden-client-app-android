package com.hidden.client.ui.fragments.home.shortlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCProfile
import com.hidden.client.models_.HCSkill
import com.hidden.client.ui.adapters.HCProfileViewPagerAdapter
import com.hidden.client.ui.activities.HomeActivity
import android.view.animation.AnimationUtils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.widget.Toast
import com.hidden.client.datamodels.HCShortlistResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.viewpagerindicator.CirclePageIndicator

class HCShortlistsFragment___ : Fragment(), View.OnClickListener {

    private lateinit var textHello: TextView
    private lateinit var textProfileCount: TextView

    // Layout: Empty & ViewPager
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var layoutViewPager: LinearLayout

    // Viewpager for Profile Sliding
    private lateinit var viewPagerNewProfile: ViewPager
    private var profileList: MutableList<HCProfile> = mutableListOf()
    private lateinit var profileAdapter: HCProfileViewPagerAdapter

    // Filter Layout
    private lateinit var layoutFilterContainer: LinearLayout
    private lateinit var layoutFilterPanel: ConstraintLayout
    private lateinit var imgOpenFilter: ImageView
    private lateinit var imgCloseFilter: ImageView

    // Swipe Container
    private lateinit var swipeContainer: HorizontalSwipeRefreshLayout

    // View Pager Indicator
    private lateinit var indicator: CirclePageIndicator

    // View Model
    private lateinit var workExperienceViewModel: HCWorkExperienceViewModel

    private lateinit var layoutBackground: ConstraintLayout

    private lateinit var retrofitCall: Call<HCShortlistResponse>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_shortlists, container, false)

        textHello = root.findViewById(R.id.text_hello)
        textHello.setText(resources.getString(R.string.hello_user, AppPreferences.myFullName))

        textProfileCount = root.findViewById(R.id.text_new_profile_count)

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
        swipeContainer.setOnRefreshListener(HorizontalSwipeRefreshLayout.OnRefreshListener {
            getShortlist()
        })

        // View Pager
        viewPagerNewProfile = root.findViewById(R.id.viewpager_new_profile)
        indicator = root.findViewById(R.id.indicator)

        layoutBackground = root.findViewById(R.id.layout_background)

        // View Models
        workExperienceViewModel =
            ViewModelProviders.of(this).get(HCWorkExperienceViewModel::class.java)

        getShortlist()

        return root
    }

    private fun getShortlist() {
        // Fetch Dashboard API
        retrofitCall = RetrofitClient.instance.getShortlists(AppPreferences.apiAccessToken)
        retrofitCall.enqueue(object : Callback<HCShortlistResponse> {
            override fun onFailure(call: Call<HCShortlistResponse>, t: Throwable) {
                if (activity !== null) {
                    Toast.makeText(activity!!.applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(
                call: Call<HCShortlistResponse>,
                response: Response<HCShortlistResponse>
            ) {
                profileList.clear()

                if (response.isSuccessful) {
                    HCGlobal.getInstance().currentShortlist = response.body()!!.candidates

                    textProfileCount.text = context!!.resources.getQuantityString(
                        R.plurals.shortlists_profile_count,
                        HCGlobal.getInstance().currentShortlist.size,
                        HCGlobal.getInstance().currentShortlist.size
                    )

                    for (candidate in HCGlobal.getInstance().currentShortlist) {
                        var profile: HCProfile = HCProfile()

                        profile.setId(candidate.candidate__candidate_id)
                        profile.setPhoto(candidate.avatar__image.safeValue())
                        profile.setTitle(candidate.avatar__name.safeValue())
                        profile.setLocation(candidate.candidate_city__name.safeValue())
                        profile.setFeedback(candidate.candidate__hidden_says.safeValue())
                        profile.setAvatarColor(candidate.avatar__colour)

                        profile.setJobTitles(
                            arrayOf(
                                candidate.job_title_1__name,
                                candidate.job_title_2__name,
                                candidate.job_title_3__name
                            )
                        )

                        var employeeHistoryList: ArrayList<String> = arrayListOf()
                        for (brand in candidate.candidate__brands) {
                            employeeHistoryList.add(brand.asset__cloudinary_url)
                        }
                        profile.setEmployeeHistory(employeeHistoryList.toTypedArray())

                        var projectList: ArrayList<String> = arrayListOf()
                        for (project in candidate.candidate__projects) {

                            val mainImage = project.candidate__project_assets.filter { it.project_asset__is_main_image }
                            if (mainImage.isNotEmpty()) {
                                projectList.add(mainImage[0].project_asset__cloudinary_url)
                            }
                        }
                        profile.setProjects(projectList.toTypedArray())

                        var skillList: ArrayList<HCSkill> = arrayListOf()
                        for (skill in candidate.candidate__skills) {
                            skillList.add(
                                HCSkill(
                                    skill.skill__name,
                                    skill.candidate_skill__ranking
                                )
                            )
                        }
                        profile.setSkills(skillList.toTypedArray())

                        profileList.add(profile)
                    }
                }

                if (profileList.size > 0) {

                    initViewPager()

                    layoutViewPager.visibility = View.VISIBLE
                    layoutEmpty.visibility = View.GONE

                    layoutBackground.setBackgroundResource(
                        resources.getIdentifier(
                            profileList[0].getAvatarColor(),
                            "drawable",
                            context!!.packageName
                        )
                    )
                } else {
                    layoutViewPager.visibility = View.GONE
                    layoutEmpty.visibility = View.VISIBLE
                }

                swipeContainer.isRefreshing = false
            }
        })
    }

    private fun initViewPager() {

        viewPagerNewProfile.pageMargin = 10

        profileAdapter = HCProfileViewPagerAdapter(
            activity!!.applicationContext,
            profileList,
            this@HCShortlistsFragment___
        )
        viewPagerNewProfile.adapter = profileAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerNewProfile)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

        if (profileList.size > HCGlobal.getInstance().currentIndex) {
            indicator.setCurrentItem(HCGlobal.getInstance().currentIndex)
            layoutBackground.setBackgroundResource(
                resources.getIdentifier(
                    profileList[HCGlobal.getInstance().currentIndex].getAvatarColor(),
                    "drawable",
                    context!!.packageName
                )
            )
        }


        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                layoutBackground.setBackgroundResource(
                    resources.getIdentifier(
                        profileList[position].getAvatarColor(),
                        "drawable",
                        context!!.packageName
                    )
                )
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
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

    override fun onPause() {

        retrofitCall.cancel()
        super.onPause()
    }
}