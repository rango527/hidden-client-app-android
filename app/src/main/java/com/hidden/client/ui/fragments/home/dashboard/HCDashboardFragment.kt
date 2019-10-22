package com.hidden.client.ui.fragments.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCDashboardResponse
import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCJob
import com.hidden.client.ui.adapters.HCColleagueJobAdapter
import com.hidden.client.ui.adapters.HCYourJobAdapter
import com.hidden.client.ui.fragments.settings.HCSettingsFragment
import com.hidden.client.ui.viewmodels.HCColleagueJobViewModel
import com.hidden.client.ui.viewmodels.HCYourJobViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCDashboardFragment : Fragment(), View.OnClickListener {

    // RecyclerView for Your Jobs
    private lateinit var rvYourJobs: RecyclerView
    private lateinit var yourJobViewModel: HCYourJobViewModel
    private lateinit var yourJobAdapter: HCYourJobAdapter
    private lateinit var layoutYourJobManager: RecyclerView.LayoutManager

    // RecyclerView for Your Jobs
    private lateinit var rvColleagueJobs: RecyclerView
    private lateinit var colleagueJobViewModel: HCColleagueJobViewModel
    private lateinit var colleagueJobAdapter: HCColleagueJobAdapter
    private lateinit var layoutColleagueJobManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        // Imageview for jumping Setting Fragment
        val imgSetting: ImageView = root.findViewById(R.id.img_settings)
        imgSetting.setOnClickListener(this)

        RetrofitClient.instance.getDashboard(HCGlobal.getInstance().myInfo.getBearerToken())
            .enqueue(object: Callback<List<HCDashboardResponse>> {
                override fun onFailure(call: Call<List<HCDashboardResponse>>, t: Throwable) {
                    Toast.makeText(activity!!.applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<HCDashboardResponse>>,
                    response: Response<List<HCDashboardResponse>>
                ) {
                    if (response.isSuccessful) {
                        initRecyclerView(root)
                    } else {
                        Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })

        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_settings -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCSettingsFragment()).commit()
            }
        }
    }

    fun initRecyclerView(root: View) {
        // Your Jobs RecyclerView
        rvYourJobs = root.findViewById(R.id.recyclerview_your_jobs)
        yourJobViewModel = ViewModelProviders.of(this).get(HCYourJobViewModel::class.java)
        yourJobViewModel.getYourJobList().observe(this, Observer {yourJobViewModels->
            yourJobAdapter = HCYourJobAdapter(activity!!.applicationContext, yourJobViewModels)

            layoutYourJobManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
            rvYourJobs.layoutManager = layoutYourJobManager
            rvYourJobs.setHasFixedSize(true)

            rvYourJobs.adapter = yourJobAdapter
        })

        // Colleague Job RecyclerView
        rvColleagueJobs = root.findViewById(R.id.recyclerview_colleage_jobs)
        colleagueJobViewModel = ViewModelProviders.of(this).get(HCColleagueJobViewModel::class.java)
        colleagueJobViewModel.getColleagueJobList().observe(this, Observer {colleagueJobViewModels->
            colleagueJobAdapter = HCColleagueJobAdapter(activity!!.applicationContext, colleagueJobViewModels)

            layoutColleagueJobManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
            rvColleagueJobs.layoutManager = layoutColleagueJobManager
            rvColleagueJobs.setHasFixedSize(true)

            rvColleagueJobs.adapter = colleagueJobAdapter
        })
    }
}