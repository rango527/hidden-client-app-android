package com.hidden.client.ui.custom

import android.content.Context
import android.view.View
import android.widget.ImageView
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
import com.hidden.client.datamodels.HCDashboardResponse
import com.hidden.client.datamodels.HCJobResponse
import com.hidden.client.enums.JobType
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCJob
import com.hidden.client.ui.adapters.HCJobAdapter
import com.hidden.client.ui.viewmodels___.HCJobViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCJobTileView : LinearLayout {

    private lateinit var txtJobName: TextView

    private lateinit var rvJob: RecyclerView
    private lateinit var jobViewModel: HCJobViewModel
    private lateinit var jobAdapter: HCJobAdapter
    private lateinit var layoutJobManager: RecyclerView.LayoutManager

    constructor(context: Context, fragment: Fragment, data: HCDashboardResponse? = null) : super(context) {
        inflate(context, R.layout.dashboard_tile_job_view, this)
        if (data != null) {

            txtJobName = findViewById(R.id.text_job);
            txtJobName.setText(data.title)

            rvJob = findViewById(R.id.recyclerview_jobs)

            jobViewModel = ViewModelProviders.of(fragment).get(HCJobViewModel::class.java)
            jobViewModel.getJobList(data.title).observe(fragment, Observer {jobViewModel ->
                jobAdapter = HCJobAdapter(context, jobViewModel)

                layoutJobManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvJob.layoutManager = layoutJobManager
                rvJob.setHasFixedSize(true)

                rvJob.adapter = jobAdapter
            })

            val layoutEmpty: LinearLayout = findViewById(R.id.layout_empty)
            val imgEmpty: ImageView = findViewById(R.id.image_empty)
            val txtEmpty: TextView = findViewById(R.id.text_empty)

            if (data.title == JobType.YOUR_JOB.value) {
                if (data.content.size === 0) {
                    rvJob.visibility = View.GONE
                    layoutEmpty.visibility = View.VISIBLE
                    imgEmpty.setImageResource(R.drawable.empty_your_jobs)
                    txtEmpty.text = data.empty_status
                } else {
                    jobViewModel.setJobList(setViewData(data.content, data.title), data.title)
                }
            } else {
                RetrofitClient.instance.getJobs(data.url, AppPreferences.apiAccessToken)
                    .enqueue(object: Callback<List<HCJobResponse>> {
                        override fun onFailure(call: Call<List<HCJobResponse>>, t: Throwable) {
                            Toast.makeText(context, "Failed...", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<List<HCJobResponse>>,
                            response: Response<List<HCJobResponse>>
                        ) {
                            if (response.isSuccessful) {
                                if (!response.body()!!.isEmpty()) {
                                    jobViewModel.setJobList(setViewData(response.body()!!, data.title), data.title)
                                } else {
                                    rvJob.visibility = View.GONE
                                    layoutEmpty.visibility = View.VISIBLE
                                    imgEmpty.setImageResource(R.drawable.empty_colleagues_jobs)
                                    txtEmpty.text = data.empty_status
                                }
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }

    private fun setViewData(jobResponseList: List<HCJobResponse>, jobType: String): ArrayList<HCJob> {

        var jobList: ArrayList<HCJob> = arrayListOf()

        for (res in jobResponseList) {
            var job: HCJob = HCJob(res.photo, res.tag, res.tag_colour, res.title,
                res.subtitle, res.extra.job_id, jobType)
            jobList.add(job)
        }

        return jobList
    }

}