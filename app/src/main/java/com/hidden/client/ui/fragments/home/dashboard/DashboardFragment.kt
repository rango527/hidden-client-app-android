package com.hidden.client.ui.fragments.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.ui.custom.dashboard.HCDatetimeLocationTileView
import com.hidden.client.ui.custom.dashboard.HCNumberTileView
import com.hidden.client.ui.fragments.settings.HCSettingsFragment
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardVM

class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var imgSetting: ImageView;
    private lateinit var layoutScrollContent: LinearLayout

    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var viewModel: DashboardVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        initUI(root)

        // Init ViewModel
        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(DashboardVM::class.java)

        viewModel.loadingVisibility.observe(this, Observer { visibility ->
            if (!visibility) {
                swipeContainer.isRefreshing = false;
            }
        })

        viewModel.dashboardTileList.observe(this, Observer { tileEntityList ->
            if (layoutScrollContent.childCount > 0) {
                layoutScrollContent.removeAllViewsInLayout()
            }

            for (tileEntity in tileEntityList) {
                when(tileEntity.type) {
                    Enums.TileType.DATETIME_LOCATION_TILE_LIST.value -> {

                        var tileView = HCDatetimeLocationTileView(activity!!.applicationContext, tileEntity)
                        layoutScrollContent.addView(tileView)
                    }

                    Enums.TileType.NUMBER_TILE_LIST.value -> {

                        var tileView = HCNumberTileView(activity!!.applicationContext, this@DashboardFragment, tileEntity)
                        layoutScrollContent.addView(tileView)
                    }
                }
            }

//
//                TileContentType.SIMPLE_METRIC.value -> {

//            }
//
//                TileContentType.JOB.value -> {
//                var jobView = HCJobTileView(activity!!.applicationContext, this@DashboardFragment, dashboardResponse)
//                layoutScrollContent.addView(jobView)
//            }
        })

        return root
    }

    private fun initUI(root: View) {

        // ImageView for jumping Setting Fragment
        imgSetting = root.findViewById(R.id.img_settings)
        imgSetting.setOnClickListener(this)

        // Scrollview Layout
        layoutScrollContent = root.findViewById(R.id.layout_dashboard_scroll)

        // SwipeContainer
        swipeContainer = root.findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.loadDashboard(true)
        })
    }


//    private fun getDashboard() {
//        retrofitCall = RetrofitClient.instance.getDashboard(AppPreferences.apiAccessToken)
//        retrofitCall.enqueue(object: Callback<List<HCDashboardResponse>> {
//            override fun onFailure(call: Call<List<HCDashboardResponse>>, t: Throwable) {
//                if (activity !== null) {
//                    Toast.makeText(activity!!.applicationContext, "Failed...", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onResponse(
//                call: Call<List<HCDashboardResponse>>,
//                response: Response<List<HCDashboardResponse>>
//            ) {
//                if (response.isSuccessful) {
//
//
//
//                    for (dashboardResponse in response.body()!!) {
//                        when (dashboardResponse.content_type) {
//
//                            TileContentType.UPCOMING_INTERVIEW.value -> {
//                                var tileView = HCDatetimeLocationTileView(activity!!.applicationContext, dashboardResponse)
//                                layoutScrollContent.addView(tileView)
//                            }
//
//                            TileContentType.SIMPLE_METRIC.value -> {
//                                var tileView = HCNumberTileView(activity!!.applicationContext, this@DashboardFragment, dashboardResponse)
//                                layoutScrollContent.addView(tileView)
//                            }
//
//                            TileContentType.JOB.value -> {
//                                var jobView = HCJobTileView(activity!!.applicationContext, this@DashboardFragment, dashboardResponse)
//                                layoutScrollContent.addView(jobView)
//                            }
//                        }
//                    }
//                    swipeContainer.isRefreshing = false
//                } else {
//                    Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_settings -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCSettingsFragment()).commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }
}