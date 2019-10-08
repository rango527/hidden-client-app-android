package com.hidden.client.ui.fragments.home.shortlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCProfile
import com.hidden.client.models.HCSkill
import com.hidden.client.ui.adapters.HCProfileViewPagerAdapter

class HCShortlistsFragment : Fragment(), View.OnClickListener {

    private lateinit var shortlistsViewModel: HCShortlistsViewModel

    private lateinit var textHello: TextView;

    // Viewpager for Profile Sliding
    private lateinit var viewPagerNewProfile: ViewPager
    private var profileList: MutableList<HCProfile> = mutableListOf()
    private lateinit var profileAdapter: HCProfileViewPagerAdapter

    // Filter Layout
    private lateinit var layoutFilter: ConstraintLayout
    private lateinit var imgOpenFilter: ImageView
    private lateinit var imgCloseFilter: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shortlistsViewModel =
            ViewModelProviders.of(this).get(HCShortlistsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_shortlists, container, false)

        textHello = root.findViewById(R.id.text_hello)
        textHello.setText(resources.getString(R.string.hello_user, HCGlobal.getInstance(activity!!.applicationContext).g_name))

        // New Profile Sliding
        profileList.add(HCProfile())
        profileList[0].setJobTitles(arrayOf("Google", "Facebook", "Twitter"))
        profileList[0].setEmployeeHistory(arrayOf(R.drawable.facebook, R.drawable.coca, R.drawable.water))
        profileList[0].setProjects(arrayOf(R.drawable.test, R.drawable.hidden_logo_black, R.drawable.test))
        profileList[0].setSkills(arrayOf(HCSkill("Art Direction", 90), HCSkill("Product Manager", 86),
            HCSkill("Android", 93), HCSkill("Kotlin", 96),
            HCSkill("Copy Writing", 65), HCSkill("Simuation", 83)))

        profileList.add(HCProfile())
        profileList[1].setJobTitles(arrayOf("Github", "Paypal", "Microsoft"))
        profileList[1].setSkills(arrayOf(HCSkill("Android Programming", 97), HCSkill("Agile", 89)))

        profileList.add(HCProfile())
        profileList[2].setJobTitles(arrayOf("Dropbox", "Amazon", "Hubstaff", "Bitbucket"))

        viewPagerNewProfile = root.findViewById(R.id.viewpager_new_profile)
        viewPagerNewProfile.clipToPadding = false
        viewPagerNewProfile.pageMargin = 32

        profileAdapter = HCProfileViewPagerAdapter(activity!!.applicationContext, profileList)
        viewPagerNewProfile.adapter = profileAdapter

        // Layout Filter
        layoutFilter = root.findViewById(R.id.layout_filter)
        imgOpenFilter = root.findViewById(R.id.img_open_filter_layout)
        imgCloseFilter = root.findViewById(R.id.img_close_filter_layout)

        imgOpenFilter.setOnClickListener(this)
        imgCloseFilter.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_open_filter_layout -> {
                layoutFilter.visibility = View.VISIBLE
            }
            R.id.img_close_filter_layout -> {
                layoutFilter.visibility = View.GONE
            }
        }
    }
}