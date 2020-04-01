package com.hidden.client.ui.fragments.process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hidden.client.R

class InterviewTileFragment () : Fragment() {


    private lateinit var interview_tile_top_layout: LinearLayout
    private lateinit var interview_tile_date_time: TextView
    private lateinit var interview_tile_title: TextView
    private lateinit var interview_tile_address: TextView
    private lateinit var interview_tile_interviwers: TextView
    private lateinit var interview_tile_interviews_count: TextView
    private lateinit var img_photo_1: ImageView
    private lateinit var img_photo_2: ImageView
    private lateinit var img_photo_3: ImageView
    private lateinit var img_photo_4: ImageView
    private lateinit var interview_tile_bottom_gmap_layout: RelativeLayout
    private lateinit var interview_tile_map_image_view: ImageView
    private lateinit var interview_tile_map_marker: ImageView
    private lateinit var interview_tile_bottom_feedback_layout: LinearLayout
    private lateinit var interview_tile_candidate_waiting_feedback: TextView
    private lateinit var interview_tile_candidate_feedback_layout: LinearLayout
    private lateinit var interview_tile_candidate_feedback_mark: TextView
    private lateinit var interview_tile_candidate_feedback_spin: TextView
    private lateinit var interview_tile_interviewer_waiting_feedback: TextView
    private lateinit var interview_tile_interviewer_feedback_layout: LinearLayout
    private lateinit var interview_tile_interviewer_feedback_mark: TextView
    private lateinit var interview_tile_interviewer_feedback_spin: TextView

    private var mode: Int??=1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.list_item_interview_tile, container, false)

        interview_tile_top_layout = view.findViewById(R.id.interview_tile_top_layout)
        interview_tile_date_time = view.findViewById(R.id.interview_tile_date_time)
        interview_tile_title = view.findViewById(R.id.interview_tile_title)
        interview_tile_address = view.findViewById(R.id.interview_tile_address)
        interview_tile_interviwers = view.findViewById(R.id.interview_tile_interviwers)
        interview_tile_interviews_count = view.findViewById(R.id.interview_tile_interviews_count)
        img_photo_1 = view.findViewById(R.id.img_photo_1)
        img_photo_2 = view.findViewById(R.id.img_photo_2)
        img_photo_3 = view.findViewById(R.id.img_photo_3)
        img_photo_4 = view.findViewById(R.id.img_photo_4)
        interview_tile_bottom_gmap_layout = view.findViewById(R.id.interview_tile_bottom_gmap_layout)
        interview_tile_map_image_view = view.findViewById(R.id.interview_tile_map_image_view)
        interview_tile_map_marker = view.findViewById(R.id.interview_tile_map_marker)
        interview_tile_bottom_feedback_layout = view.findViewById(R.id.interview_tile_bottom_feedback_layout)
        interview_tile_candidate_waiting_feedback = view.findViewById(R.id.interview_tile_candidate_waiting_feedback)
        interview_tile_candidate_feedback_layout = view.findViewById(R.id.interview_tile_candidate_feedback_layout)
        interview_tile_candidate_feedback_mark = view.findViewById(R.id.interview_tile_candidate_feedback_mark)
        interview_tile_candidate_feedback_spin = view.findViewById(R.id.interview_tile_candidate_feedback_spin)
        interview_tile_interviewer_waiting_feedback = view.findViewById(R.id.interview_tile_interviewer_waiting_feedback)
        interview_tile_interviewer_feedback_layout = view.findViewById(R.id.interview_tile_interviewer_feedback_layout)
        interview_tile_interviewer_feedback_mark = view.findViewById(R.id.interview_tile_interviewer_feedback_mark)
        interview_tile_interviewer_feedback_spin = view.findViewById(R.id.interview_tile_interviewer_feedback_spin)


        initUI()

        return view
    }

    fun setUIMode(mode: Int) {
        this.mode = mode
    }

    private fun initUI() {

        if (mode == 1) {
            interview_tile_bottom_gmap_layout.visibility = View.VISIBLE
            interview_tile_bottom_feedback_layout.visibility = View.GONE
            interview_tile_date_time.setText("Date TBC")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("Location TBC")
            interview_tile_interviwers.setText("Interviwers TBC")
            interview_tile_interviews_count.setText("")
            img_photo_1.setImageResource(R.drawable.fill_gray)
            img_photo_2.setImageResource(R.drawable.fill_gray)
            img_photo_3.setImageResource(R.drawable.fill_gray)
            img_photo_4.visibility = View.GONE
            interview_tile_map_marker.setImageResource(R.drawable.map_marker_gray)
        }
        else if (mode == 2) {
            interview_tile_bottom_gmap_layout.visibility = View.VISIBLE
            interview_tile_bottom_feedback_layout.visibility = View.GONE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.visibility = View.GONE
            interview_tile_map_marker.setImageResource(R.drawable.map_marker_green)
        }
        else if (mode == 3) {
            interview_tile_bottom_gmap_layout.visibility = View.VISIBLE
            interview_tile_bottom_feedback_layout.visibility = View.GONE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("+2")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.setImageResource(R.drawable.man)
            interview_tile_map_marker.setImageResource(R.drawable.map_marker_green)
        }
        else if (mode == 4) {
            interview_tile_bottom_gmap_layout.visibility = View.GONE
            interview_tile_bottom_feedback_layout.visibility = View.VISIBLE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("+2")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.setImageResource(R.drawable.man)
            interview_tile_candidate_waiting_feedback.visibility = View.VISIBLE
            interview_tile_candidate_feedback_layout.visibility = View.GONE
            interview_tile_interviewer_waiting_feedback.visibility = View.VISIBLE
            interview_tile_interviewer_feedback_layout.visibility = View.GONE
        }
        else if (mode == 5) {
            interview_tile_bottom_gmap_layout.visibility = View.GONE
            interview_tile_bottom_feedback_layout.visibility = View.VISIBLE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("+2")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.setImageResource(R.drawable.man)
            interview_tile_candidate_waiting_feedback.visibility = View.GONE
            interview_tile_candidate_feedback_layout.visibility = View.VISIBLE
            interview_tile_candidate_feedback_mark.setText("3.2")
            interview_tile_candidate_feedback_spin.setText(R.string.pulled_out)
            interview_tile_candidate_feedback_spin.setBackgroundResource(R.drawable.button_round_red)
            interview_tile_interviewer_waiting_feedback.visibility = View.GONE
            interview_tile_interviewer_feedback_layout.visibility = View.VISIBLE
            interview_tile_interviewer_feedback_mark.setText("3.5")
            interview_tile_interviewer_feedback_spin.setText(R.string.decision_pending)
            interview_tile_interviewer_feedback_spin.setBackgroundResource(R.drawable.button_round_gray)
        }
        else if (mode == 6) {
            interview_tile_bottom_gmap_layout.visibility = View.GONE
            interview_tile_bottom_feedback_layout.visibility = View.VISIBLE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("+2")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.setImageResource(R.drawable.man)
            interview_tile_candidate_waiting_feedback.visibility = View.GONE
            interview_tile_candidate_feedback_layout.visibility = View.VISIBLE
            interview_tile_candidate_feedback_mark.setText("3.2")
            interview_tile_candidate_feedback_spin.setText(R.string.pulled_out)
            interview_tile_candidate_feedback_spin.setBackgroundResource(R.drawable.button_round_red)
            interview_tile_interviewer_waiting_feedback.visibility = View.GONE
            interview_tile_interviewer_feedback_layout.visibility = View.VISIBLE
            interview_tile_interviewer_feedback_mark.setText("3.5")
            interview_tile_interviewer_feedback_spin.setText(R.string.pulled_out)
            interview_tile_interviewer_feedback_spin.setBackgroundResource(R.drawable.button_round_red)
        }
        else if (mode == 7) {
            interview_tile_bottom_gmap_layout.visibility = View.GONE
            interview_tile_bottom_feedback_layout.visibility = View.VISIBLE
            interview_tile_date_time.setText("9:30 on 30th Match '20")
            interview_tile_title.setText("First Stage Interview")
            interview_tile_address.setText("4 Elder Street, London, E1 6BT")
            interview_tile_interviwers.setText("Interviwers")
            interview_tile_interviews_count.setText("+2")
            img_photo_1.setImageResource(R.drawable.man)
            img_photo_2.setImageResource(R.drawable.man)
            img_photo_3.setImageResource(R.drawable.man)
            img_photo_4.setImageResource(R.drawable.man)
            interview_tile_candidate_waiting_feedback.visibility = View.GONE
            interview_tile_candidate_feedback_layout.visibility = View.VISIBLE
            interview_tile_candidate_feedback_mark.setText("4.2")
            interview_tile_candidate_feedback_spin.setText(R.string.progressing)
            interview_tile_candidate_feedback_spin.setBackgroundResource(R.drawable.button_round_green)
            interview_tile_interviewer_waiting_feedback.visibility = View.GONE
            interview_tile_interviewer_feedback_layout.visibility = View.VISIBLE
            interview_tile_interviewer_feedback_mark.setText("4.7")
            interview_tile_interviewer_feedback_spin.setText(R.string.progressing)
            interview_tile_interviewer_feedback_spin.setBackgroundResource(R.drawable.button_round_green)
        }
    }
}