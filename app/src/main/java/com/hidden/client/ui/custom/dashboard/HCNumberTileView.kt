package com.hidden.client.ui.custom.dashboard

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCMetricsResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.DashboardTileEntity
import com.hidden.client.ui.adapters.HCMetricAdapter
import com.hidden.client.ui.viewmodels___.HCMetricViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCNumberTileView : LinearLayout {

    private lateinit var txtMetricName: TextView

    private lateinit var rvMetric: RecyclerView
    private lateinit var metricViewModel: HCMetricViewModel
    private lateinit var metricAdapter: HCMetricAdapter
    private lateinit var layoutMetricManager: RecyclerView.LayoutManager

    constructor(context: Context, fragment: Fragment, data: DashboardTileEntity? = null) : super(context) {
        inflate(context, R.layout.dashboard_number_tile_view, this)
        if (data != null) {

            txtMetricName = findViewById(R.id.text_metric_name);
            txtMetricName.text = data.title

            rvMetric = findViewById(R.id.recyclerview_metric)

            metricViewModel = ViewModelProviders.of(fragment).get(HCMetricViewModel::class.java)

            metricViewModel.getMetricList(data.title).observe(fragment, Observer {metricViewModel ->
                metricAdapter = HCMetricAdapter(context, metricViewModel)

                layoutMetricManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvMetric.layoutManager = layoutMetricManager
                rvMetric.setHasFixedSize(true)

                rvMetric.adapter = metricAdapter
            })

//            RetrofitClient.instance.getMetrics(data.url, AppPreferences.apiAccessToken)
//                .enqueue(object: Callback<List<HCMetricsResponse>> {
//                    override fun onFailure(call: Call<List<HCMetricsResponse>>, t: Throwable) {
//                        Toast.makeText(context, "Failed...", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onResponse(
//                        call: Call<List<HCMetricsResponse>>,
//                        response: Response<List<HCMetricsResponse>>
//                    ) {
//                        if (response.isSuccessful) {
//                            if (response.body()!!.isEmpty()) {
//
//                            } else {
//                                metricViewModel.setMetricList(response.body()!!, data.title, data.color_scheme)
//                            }
//                        } else {
//                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                })
        }
    }

}