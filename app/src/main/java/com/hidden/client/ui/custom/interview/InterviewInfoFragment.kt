package com.hidden.client.ui.custom.interview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.InterviewEntity
import com.hidden.client.ui.activities.JobReviewerTypeActivity
import com.hidden.client.ui.activities.process.AddInterviewersActivity
import com.hidden.client.ui.adapters.InterviewerItemListAdapter
import java.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class InterviewInfoFragment (
    private val mContext: Context,
    private val interview: InterviewEntity
) : Fragment(), OnMapReadyCallback, View.OnClickListener {


    private lateinit var questionButton: Button
    private lateinit var addInterviewerButton: Button

    private lateinit var noInterviewersLayout: LinearLayout

    private lateinit var noInterviewersTextView: TextView
    private lateinit var addInterviewerButtonRed: Button

    private lateinit var interviewersRecyclerView: RecyclerView

    private lateinit var justConfirmingTextView: TextView

    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView

    private lateinit var bottomLayout: ConstraintLayout

    private lateinit var mapView: MapView

    private lateinit var markerImage: ImageView

    private lateinit var locationTextView: TextView

    var map: GoogleMap? = null
    private var latLng:LatLng? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_interview_info, container, false)

        initUI(view)

        setUI()

        mapView.onCreate(savedInstanceState);
        mapView.onResume()
        mapView.getMapAsync(this)

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        return view
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
            R.id.interview_info_question -> {
                //
                val intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerTypeActivity::class.java)
                intent.putExtra("reviewType", 6)
                startActivity(intent)
            }
            R.id.interview_info_add_interviewer -> {
                //
                addInterviewer()
            }
            R.id.interview_info_add_interviewer_button -> {
                //

                addInterviewer()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {

        this.map = map
        this.map!!.uiSettings.isMyLocationButtonEnabled = false

        if (latLng != null) {

            this.map!!.addMarker(MarkerOptions().position(latLng!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_full)))
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng!!, 14f)
            map.animateCamera(cameraUpdate)
        }
    }

    private fun initUI(view: View) {

        questionButton = view.findViewById(R.id.interview_info_question)
        questionButton.setOnClickListener(this)

        addInterviewerButton = view.findViewById(R.id.interview_info_add_interviewer)
        addInterviewerButton.setOnClickListener(this)

        noInterviewersLayout = view.findViewById(R.id.interview_info_no_interviewers_layout)

        noInterviewersTextView = view.findViewById(R.id.interview_info_no_interviewers)

        addInterviewerButtonRed = view.findViewById(R.id.interview_info_add_interviewer_button)
        addInterviewerButtonRed.setOnClickListener(this)

        interviewersRecyclerView = view.findViewById(R.id.interview_info_interviewers)

        justConfirmingTextView = view.findViewById(R.id.interview_info_just_confirming)

        timeTextView = view.findViewById(R.id.interview_info_time)
        dateTextView = view.findViewById(R.id.interview_info_date)

        bottomLayout = view.findViewById(R.id.interview_info_bottom_layout)

        mapView = view.findViewById(R.id.map)
        markerImage = view.findViewById(R.id.image_marker)

        locationTextView = view.findViewById(R.id.interview_info_location)

    }

    private fun setUI() {

        // Creates a vertical Layout Manager
        interviewersRecyclerView.layoutManager = LinearLayoutManager(mContext)

//        interviewersRecyclerView.setHasFixedSize(true)

        // Access the RecyclerView Adapter and load the data into it
        interviewersRecyclerView.adapter = InterviewerItemListAdapter( mContext, interview.getInterviewerList())

        if (interview.getInterviewerList().isNotEmpty()) {
            noInterviewersLayout.visibility = View.GONE
            interviewersRecyclerView.visibility = View.VISIBLE
        }
        else {
            noInterviewersLayout.visibility = View.VISIBLE
            interviewersRecyclerView.visibility = View.GONE
        }

        setDateTime()

        // map view latLng
        if (!interview.latLng.isNullOrEmpty()) {
            val latLngs = interview.latLng.split(",")
            if (latLngs.size == 2) {
                latLng = LatLng(
                    latLngs[0].toDouble(),
                    latLngs[1].toDouble()
                )

                mapView.visibility = View.VISIBLE
                markerImage.visibility = View.GONE
            }
        }

        locationTextView.text = if (interview.location.isNullOrEmpty()) "" else interview.location
    }

    private fun addInterviewer() {
        //
        val intent = Intent(HCGlobal.getInstance().currentActivity, AddInterviewersActivity::class.java)
        intent.putExtra("from", "Add")
        intent.putExtra("processId", interview.pProcessId)
//        intent.putExtra("jobId", jobId)
        intent.putExtra("interviewId", interview.interviewId)
        intent.putExtra("candidateName", interview.fullName)
        startActivity(intent)
    }

    private fun setDateTime() {

        val interviewDate: Date? = HCDate.convertUTCDateStringToLocal(interview.dateTime.safeValue(), null)

        if (interviewDate == null) {

            timeTextView.visibility = View.GONE
            dateTextView.visibility = View.GONE
            justConfirmingTextView.visibility = View.VISIBLE
        } else {

            val date: String = HCDate.dateToString(interviewDate,"EEEE").safeValue()
            val day: String = HCDate.dateToString(interviewDate,"d").safeValue()
            val month: String =  HCDate.dateToString(interviewDate, "MMMM").safeValue()
            val year: String = HCDate.dateToString(interviewDate, "yyyy").safeValue()
            val hour: String = HCDate.dateToString(interviewDate, "HH").safeValue()
            val minute: String = HCDate.dateToString(interviewDate, "mm").safeValue()
            val interviewDateText = "$date, $month $day, $year"
            val interviewTimeText = "$hour:$minute"

            timeTextView.text = interviewTimeText
            dateTextView.text = interviewDateText
        }
    }

}