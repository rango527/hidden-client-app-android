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
import com.hidden.client.datamodels.HCDashboardResponse
import com.hidden.client.datamodels.HCShortlistCandidateResponse
import com.hidden.client.datamodels.HCShortlistResponse
import com.hidden.client.enums.TileContentType
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.json.ShortlistJson
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.custom.HCInterviewTileView
import com.hidden.client.ui.custom.HCJobTileView
import com.hidden.client.ui.custom.HCNumberTileView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HCShortlistsFragment : Fragment(), View.OnClickListener {

    private lateinit var textHello: TextView;

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_shortlists, container, false)

        textHello = root.findViewById(R.id.text_hello)
        textHello.setText(resources.getString(R.string.hello_user, AppPreferences.myFullName))

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

        // New Profile Sliding
        profileList.add(HCProfile())
        profileList[0].setJobTitles(arrayOf("Google", "Facebook", "Twitter"))
//        profileList[0].setEmployeeHistory(
//            arrayOf(
//                R.drawable.facebook,
//                R.drawable.coca,
//                R.drawable.water
//            )
//        )
        profileList[0].setProjects(
            arrayOf(
                "https://res.cloudinary.com/dioyg7htb/image/upload/v1534146352/hidden/brands/737/logo/5b71372f6bcca.jpg",
                "https://res.cloudinary.com/dioyg7htb/image/upload/v1534146352/hidden/brands/737/logo/5b71372f6bcca.jpg"
            )
        )
        profileList[0].setSkills(
            arrayOf(
                HCSkill("Art Direction", 90), HCSkill("Product Manager", 86),
                HCSkill("Android", 93), HCSkill("Kotlin", 96),
                HCSkill("Copy Writing", 65), HCSkill("Simuation", 83)
            )
        )

        profileList.add(HCProfile())
        profileList[1].setJobTitles(arrayOf("Github", "Paypal", "Microsoft"))
        profileList[1].setSkills(arrayOf(HCSkill("Android Programming", 97), HCSkill("Agile", 89)))

        profileList.add(HCProfile())
        profileList[2].setJobTitles(arrayOf("Dropbox", "Amazon", "Hubstaff", "Bitbucket"))

        if (profileList.size > 0) {
            viewPagerNewProfile = root.findViewById(R.id.viewpager_new_profile)
            viewPagerNewProfile.clipToPadding = false
            viewPagerNewProfile.pageMargin = 32

            profileAdapter = HCProfileViewPagerAdapter(activity!!.applicationContext, profileList)
            viewPagerNewProfile.adapter = profileAdapter

            layoutViewPager.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE

        } else {
            layoutViewPager.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        }

        // Fetch Dashboard API
        RetrofitClient.instance.getShortlists(AppPreferences.apiAccessToken)
            .enqueue(object : Callback<HCShortlistResponse> {
                override fun onFailure(call: Call<HCShortlistResponse>, t: Throwable) {
                    Toast.makeText(activity!!.applicationContext, "Failed...", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<HCShortlistResponse>,
                    response: Response<HCShortlistResponse>
                ) {
                    if (response.isSuccessful) {
                        val candidateList: List<HCShortlistCandidateResponse> =
                            response.body()!!.candidates

                        for (candidate in candidateList) {
                            var profile: HCProfile = HCProfile()
                            profile.setJobTitles(
                                arrayOf(
                                    candidate.job_title_1__name,
                                    candidate.job_title_2__name,
                                    candidate.job_title_3__name
                                )
                            )

                            var employeeHistoryList: ArrayList<String> = arrayListOf()
                            for (workExperience in candidate.candidate__work_experiences) {
                                employeeHistoryList.add(workExperience.asset__cloudinary_url)
                            }
                            profile.setEmployeeHistory(*employeeHistoryList.toTypedArray())
                        }


                    }
                }
            })
        return root
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
}