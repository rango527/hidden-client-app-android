package com.hidden.client.ui.fragments.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCDashboardResponse
import com.hidden.client.enums.TileContentType
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.custom.HCInterviewTileView
import com.hidden.client.ui.custom.HCJobTileView
import com.hidden.client.ui.custom.HCNumberTileView
import com.hidden.client.ui.fragments.settings.HCSettingsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var imgSetting: ImageView;
    private lateinit var layoutScrollContent: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        // Imageview for jumping Setting Fragment
        imgSetting = root.findViewById(R.id.img_settings)
        imgSetting.setOnClickListener(this)

        // Scrollview Layout
        layoutScrollContent = root.findViewById(R.id.layout_dashboard_scroll)

        // Fetch Dashboard API
        RetrofitClient.instance.getDashboard(AppPreferences.apiAccessToken)
            .enqueue(object: Callback<List<HCDashboardResponse>> {
                override fun onFailure(call: Call<List<HCDashboardResponse>>, t: Throwable) {
                    Toast.makeText(activity!!.applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<HCDashboardResponse>>,
                    response: Response<List<HCDashboardResponse>>
                ) {
                    if (response.isSuccessful) {
                        for (dashboardResponse in response.body()!!) {
                            when (dashboardResponse.content_type) {

                                TileContentType.UPCOMING_INTERVIEW.value -> {
                                    var tileView = HCInterviewTileView(activity!!.applicationContext, dashboardResponse)
                                    layoutScrollContent.addView(tileView)
                                }

                                TileContentType.SIMPLE_METRIC.value -> {
                                    var tileView = HCNumberTileView(activity!!.applicationContext, this@DashboardFragment, dashboardResponse)
                                    layoutScrollContent.addView(tileView)
                                }

                                TileContentType.JOB.value -> {
                                    var jobView = HCJobTileView(activity!!.applicationContext, this@DashboardFragment, dashboardResponse)
                                    layoutScrollContent.addView(jobView)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })

        return root
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_settings -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCSettingsFragment()).commit()
            }
        }
    }
}