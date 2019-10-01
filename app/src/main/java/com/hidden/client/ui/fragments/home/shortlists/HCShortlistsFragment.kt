package com.hidden.client.ui.fragments.home.shortlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCProfile
import com.hidden.client.ui.adapters.HCProfileViewPagerAdapter

class HCShortlistsFragment : Fragment() {

    private lateinit var shortlistsViewModel: HCShortlistsViewModel

    private lateinit var textHello: TextView;

    // Viewpager for Profile Sliding
    private lateinit var viewPagerNewProfile: ViewPager
    private var profileList: MutableList<HCProfile> = mutableListOf()
    private lateinit var profileAdapter: HCProfileViewPagerAdapter

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

        profileList.add(HCProfile())
        profileList[1].setJobTitles(arrayOf("Github", "Paypal", "Microsoft"))

        profileList.add(HCProfile())
        profileList[2].setJobTitles(arrayOf("Dropbox", "Amazon", "Hubstaff", "Bitbucket"))

        viewPagerNewProfile = root.findViewById(R.id.viewpager_new_profile)
        viewPagerNewProfile.clipToPadding = false
        viewPagerNewProfile.pageMargin = 32

        profileAdapter = HCProfileViewPagerAdapter(activity!!.applicationContext, profileList)
        viewPagerNewProfile.adapter = profileAdapter

        return root
    }
}