package com.hidden.client.ui.custom.process

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.MapView
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.adapters.NumberTileListAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardTileListVM
import kotlinx.android.synthetic.main.view_dashboard_number_tile.view.*
import kotlinx.android.synthetic.main.view_process_interview_map_tile.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class TimelineInterviewMapTileView(
    context: Context,
    fragment: Fragment,
    private val data: TimelineEntity
) :
    LinearLayout(context) {

    private lateinit var txtDate: TextView
    private lateinit var txtInterview: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtInterviewers: TextView
    private lateinit var layoutInterviewers: ConstraintLayout
    private lateinit var mapView: MapView
    private lateinit var imgMarker: ImageView

    init {
        inflate(context, R.layout.view_process_interview_map_tile, this)

        initView()

        makeTileView()
    }

    private fun initView() {
        txtDate = findViewById(R.id.text_date)
        txtInterview = findViewById(R.id.text_interview)
        txtLocation = findViewById(R.id.text_location)
        txtInterviewers = findViewById(R.id.text_interviewer)
        layoutInterviewers = findViewById(R.id.layout_interviewer)
        mapView = findViewById(R.id.map)
        imgMarker = findViewById(R.id.image_marker)
    }

    private fun makeTileView() {
        txtInterview.text = data.description

        if (data.dateTime.safeValue() == "" && data.location.safeValue() == "" && data.getInterviewParticipantList().isEmpty() && data.latLng.safeValue() == "") {
            txtDate.text = context.getString(R.string.date_tbc)
            txtInterview.text = data.description
            txtInterviewers.text = context.getString(R.string.interviewers_tbc)

            mapView.visibility = View.GONE
            imgMarker.visibility = View.VISIBLE
        }

        if (data.dateTime.safeValue() != "") {
            val interviewDate: Date? = HCDate.stringToDate(data.dateTime!!, null)

            val time: String = HCDate.dateToString(interviewDate!!, "H:m").safeValue()
            val day: String = HCDate.dayPrefix(HCDate.dateToString(interviewDate,"d").safeValue())
            val month: String =  HCDate.dateToString(interviewDate, "MMMM").safeValue()
            val year: String = HCDate.dateToString(interviewDate, "yy").safeValue()
            val interviewDateText = "$time on $day $month '$year"

            txtDate.text = interviewDateText
            txtLocation.text = data.location
            txtInterviewers.text = context.getText(R.string.interviewers)

            mapView.visibility = View.VISIBLE
            imgMarker.visibility = View.GONE
        }
    }
}