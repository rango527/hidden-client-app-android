package com.hidden.client.ui.custom.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.ui.adapters.NumberTileListAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardTileListVM
import kotlinx.android.synthetic.main.view_dashboard_number_tile.view.*

@SuppressLint("ViewConstructor")
class HCNumberTileView(context: Context, fragment: Fragment, data: DashboardTileEntity? = null) :
    LinearLayout(context) {

    private var txtTitle: TextView
    private var viewModel: DashboardTileListVM

    private lateinit var adapter: NumberTileListAdapter

    init {

        inflate(context, R.layout.view_dashboard_number_tile, this)

        txtTitle = findViewById(R.id.text_metric_name)
        txtTitle.text = data!!.title

        viewModel = ViewModelProviders.of(fragment, ViewModelFactory(context)).get(DashboardTileListVM::class.java)
        viewModel.loadDashboardTileContent(data)

        viewModel.getNumberTileListData(data.title).observe(fragment, Observer { metricsList ->

            adapter = NumberTileListAdapter(metricsList)

            recyclerview_number_tile.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerview_number_tile.setHasFixedSize(true)

            recyclerview_number_tile.adapter = adapter
        })
    }
}