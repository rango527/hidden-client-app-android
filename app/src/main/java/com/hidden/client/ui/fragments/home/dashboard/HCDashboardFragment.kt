package com.hidden.client.ui.fragments.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.models.HCJob
import com.hidden.client.ui.adapters.HCYourJobAdapter
import com.hidden.client.ui.fragments.settings.HCSettingsFragment
import kotlinx.android.synthetic.main.fragment_home_dashboard.*

class HCDashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var dashboardViewModel: HCDashboardViewModel

    // RecyclerView for Your Jobs
    private lateinit var recyclerViewYourJobs: RecyclerView
    private var yourJobList: MutableList<HCJob> = mutableListOf()
    private lateinit var yourJobAdapter: HCYourJobAdapter

    // RecyclerView for Your Jobs
    private lateinit var recyclerViewColleagueJobs: RecyclerView
    private var colleagueJobList: MutableList<HCJob> = mutableListOf()
    private lateinit var colleagueJobAdapter: HCYourJobAdapter

    private lateinit var layoutYourJobManager: RecyclerView.LayoutManager
    private lateinit var layoutColleagueJobManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dashboardViewModel =
            ViewModelProviders.of(this).get(HCDashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        // Imageview for jumping Setting Fragment
        val imgSetting: ImageView = root.findViewById(R.id.img_settings)
        imgSetting.setOnClickListener(this)

        //Your Jobs
        recyclerViewYourJobs = root.findViewById(R.id.recyclerview_your_jobs)
        layoutYourJobManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewYourJobs.layoutManager = layoutYourJobManager
        recyclerViewYourJobs.setHasFixedSize(true)

        yourJobList.add(HCJob("", "Director", "NewYork, NY"))
        yourJobList.add(HCJob("", "Vice President", "Chicago"))

        yourJobAdapter = HCYourJobAdapter(yourJobList, activity!!.applicationContext)
        recyclerViewYourJobs.adapter = yourJobAdapter

        //Colleague Jobs
        recyclerViewColleagueJobs = root.findViewById(R.id.recyclerview_colleage_jobs)
        layoutColleagueJobManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewColleagueJobs.layoutManager = layoutColleagueJobManager
        recyclerViewColleagueJobs.setHasFixedSize(true)

        colleagueJobList.add(HCJob("", "UX Designer", "London"))
        colleagueJobList.add(HCJob("", "Tech Lead", "London"))
        colleagueJobList.add(HCJob("", "Social Media Manager", "NewYork, NY"))
        colleagueJobList.add(HCJob("", "Creative Director2", ""))

        colleagueJobAdapter = HCYourJobAdapter(colleagueJobList, activity!!.applicationContext)
        recyclerViewColleagueJobs.adapter = colleagueJobAdapter

        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_settings -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCSettingsFragment()).commit()
            }
        }
    }
}