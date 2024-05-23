package com.hidden.client.ui.custom.process

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.activities.shortlist.InterviewActivity
import com.hidden.client.ui.activities.shortlist.ShortlistFeedbackActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


@SuppressLint("ViewConstructor")
class TimelineInterviewMapTileFragment(
    private val data: TimelineEntity,
    private val separator: Boolean
) : Fragment(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var txtDate: TextView
    private lateinit var txtInterview: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtInterviewers: TextView
    private lateinit var layoutInterviewers: ConstraintLayout
    private lateinit var mapView: MapView
    private lateinit var imgMarker: ImageView
    private lateinit var imgSeparator: ImageView

    private lateinit var txtInterviewerMore: TextView
    private lateinit var imgInterviewer1: CircleImageView
    private lateinit var imgInterviewer2: CircleImageView
    private lateinit var imgInterviewer3: CircleImageView
    private lateinit var imgInterviewer4: CircleImageView

    private lateinit var topLayout: LinearLayout
    private lateinit var bottomLayout: ConstraintLayout

    var map: GoogleMap? = null

    companion object {
        fun newInstance(data: TimelineEntity, separator: Boolean): TimelineInterviewMapTileFragment {
            return TimelineInterviewMapTileFragment(data, separator)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.view_process_timeline_interview_map_tile, container, false)

        txtDate = view.findViewById(R.id.text_date)
        txtInterview = view.findViewById(R.id.text_interview)
        txtLocation = view.findViewById(R.id.text_location)
        txtInterviewers = view.findViewById(R.id.text_interviewer)
        layoutInterviewers = view.findViewById(R.id.layout_interviewer)
        mapView = view.findViewById(R.id.map)
        imgMarker = view.findViewById(R.id.image_marker)
        imgSeparator = view.findViewById(R.id.image_separator)

        mapView.onCreate(savedInstanceState);
        mapView.onResume()
        mapView.getMapAsync(this)

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        txtInterviewerMore = view.findViewById(R.id.text_interviewer_more)
        imgInterviewer1 = view.findViewById(R.id.image_interviewer_1)
        imgInterviewer2 = view.findViewById(R.id.image_interviewer_2)
        imgInterviewer3 = view.findViewById(R.id.image_interviewer_3)
        imgInterviewer4 = view.findViewById(R.id.image_interviewer_4)

        topLayout = view.findViewById(R.id.layout_timeline_interview_top)
        bottomLayout = view.findViewById(R.id.layout_timeline_interview_bottom)

        initView()

        return view
    }

    private fun initView() {

        if (separator) {
            imgSeparator.visibility = View.VISIBLE
        }

        txtInterview.text = data.description

        if (data.dateTime.safeValue() == "") {
            txtDate.text = getString(R.string.date_tbc)
            txtLocation.text = getString(R.string.location_tbc)
            txtInterviewers.text = getString(R.string.interviewers_tbc)

            mapView.visibility = View.GONE
            imgMarker.visibility = View.VISIBLE
        } else {
            val interviewDate: Date? = HCDate.stringToDate(data.dateTime!!, null)

            val time: String = HCDate.dateToString(interviewDate!!, "H:m").safeValue()
            val day: String = HCDate.dayPrefix(HCDate.dateToString(interviewDate,"d").safeValue())
            val month: String =  HCDate.dateToString(interviewDate, "MMMM").safeValue()
            val year: String = HCDate.dateToString(interviewDate, "yy").safeValue()
            val interviewDateText = "$time on $day $month '$year"

            txtDate.text = interviewDateText
            txtLocation.text = data.location
            txtInterviewers.text = getString(R.string.interviewers)

            mapView.visibility = View.VISIBLE
            imgMarker.visibility = View.GONE
        }

        val interviewerCount = data.getInterviewParticipantList().size
        when {
            interviewerCount >= 4 -> {
                imgInterviewer1.visibility = View.VISIBLE
                imgInterviewer2.visibility = View.VISIBLE
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE
                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer1)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer2)
                Glide.with(this).load(data.getInterviewParticipantList()[2].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[3].clientAvatar).into(imgInterviewer4)

                if (interviewerCount > 4) {
                    txtInterviewerMore.visibility = View.VISIBLE
                    txtInterviewerMore.text = """+${(interviewerCount - 4)}"""
                }
            }
            interviewerCount == 3 -> {
                imgInterviewer2.visibility = View.VISIBLE
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE

                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer2)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[2].clientAvatar).into(imgInterviewer4)
            }
            interviewerCount == 2 -> {
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE

                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer4)
            }
            interviewerCount == 1 -> {
                imgInterviewer4.visibility = View.VISIBLE
                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer4)
            }
        }

        topLayout.setOnClickListener(this)
        bottomLayout.setOnClickListener(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        this.map!!.uiSettings.isMyLocationButtonEnabled = false

        if (data.latLng != null) {
            val latLng = data.latLng.split(",")
            if (latLng.size == 2) {
                this.map!!.addMarker(MarkerOptions().position(LatLng(latLng[0].toDouble(), latLng[1].toDouble())))
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(latLng[0].toDouble(), latLng[1].toDouble()), 14f)
                map.animateCamera(cameraUpdate)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.layout_timeline_interview_top -> {
                startInterviewActivity()
            }
            R.id.layout_timeline_interview_bottom -> {
                startInterviewActivity()
            }
        }
    }

    private fun startInterviewActivity() {

        val intent = Intent(this.activity, InterviewActivity::class.java)
        val processId = data.pProcessId!!
        var interviewId = data.interviewId!!

        intent.putExtra("processId", processId)
        intent.putExtra("interviewId", interviewId)
        startActivity(intent)
    }
}