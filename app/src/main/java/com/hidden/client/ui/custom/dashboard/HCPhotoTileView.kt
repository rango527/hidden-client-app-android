package com.hidden.client.ui.custom.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.ui.adapters.PhotoTileListAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardTileListVM

import kotlinx.android.synthetic.main.view_dashboard_photo_tile.view.*

@SuppressLint("ViewConstructor")
class HCPhotoTileView(context: Context, fragment: Fragment, data: DashboardTileEntity? = null) :
    LinearLayout(context) {

    private var txtJobName: TextView
    private var viewModel: DashboardTileListVM
    private var layoutEmpty: LinearLayout

    private lateinit var adapter: PhotoTileListAdapter

    init {
        inflate(context, R.layout.view_dashboard_photo_tile, this)

        txtJobName = findViewById(R.id.text_job)
        txtJobName.text = data!!.title

        layoutEmpty = findViewById(R.id.layout_empty)

        viewModel = ViewModelProviders.of(fragment, ViewModelFactory(context)).get(DashboardTileListVM::class.java)
        viewModel.loadDashboardTileContent(data)
        viewModel.getPhotoTileListData(data.title).observe(fragment, Observer { jobList ->

            if (jobList.size > 0) {
                adapter = PhotoTileListAdapter(context, jobList)

                recyclerview_jobs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerview_jobs.setHasFixedSize(true)

                recyclerview_jobs.adapter = adapter

                recyclerview_jobs.visibility = View.VISIBLE
                layoutEmpty.visibility = View.GONE

            }  else {

                val imgEmpty: ImageView = findViewById(R.id.image_empty)
                val txtEmpty: TextView = findViewById(R.id.text_empty)

                txtEmpty.text = data.emptyStatus

                if (data.title == Enums.TileTitle.YOUR_JOBS.value) {
                    imgEmpty.setImageResource(R.drawable.empty_your_jobs)
                } else {
                    imgEmpty.setImageResource(R.drawable.empty_colleagues_jobs)
                }

                recyclerview_jobs.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
            }
        })
    }
}