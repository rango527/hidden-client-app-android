package com.hidden.client.ui.custom.process

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
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.adapters.NumberTileListAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardTileListVM
import kotlinx.android.synthetic.main.view_dashboard_number_tile.view.*

@SuppressLint("ViewConstructor")
class TimelineInterviewFeedbackTileView(context: Context, fragment: Fragment, data: TimelineEntity? = null) :
    LinearLayout(context) {

    private lateinit var adapter: NumberTileListAdapter

    init {

        inflate(context, R.layout.view_process_interview_feedback_tile, this)
    }
}