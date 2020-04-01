package com.hidden.client.ui.fragments.process

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.activities.process.AddInterviewersActivity
import com.hidden.client.ui.custom.ProcessStageBarView
import com.hidden.client.ui.custom.ProcessStageTriangleView

class ProcessTimelineFragment(
    private val process: ProcessEntity,
    private val timelineList: List<TimelineEntity>
) : Fragment() {

    private lateinit var layoutStageControl: LinearLayout

    private lateinit var layoutTile: ConstraintLayout
    private lateinit var txtStage: TextView
    private lateinit var txtStageIcon: TextView
    private lateinit var txtAvailability: TextView
    private lateinit var txtDescription: TextView

    private lateinit var imgPrev: ImageView
    private lateinit var imgNext: ImageView

    private lateinit var processTriangleBar: ProcessStageTriangleView

    private var currentStatus: Int = 0

    lateinit var context: AppCompatActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_process_process, container, false)

        layoutStageControl = view.findViewById(R.id.layout_stage_control)

        layoutTile = view.findViewById(R.id.layout_title)
        txtStage = view.findViewById(R.id.text_stage)
        txtStageIcon = view.findViewById(R.id.text_stage_icon)
        txtAvailability = view.findViewById(R.id.text_availability)
        txtDescription = view.findViewById(R.id.text_description)

        imgPrev = view.findViewById(R.id.image_prev)
        imgNext = view.findViewById(R.id.image_next)

        val fm = context.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        fragmentTransaction = fm.beginTransaction()
        val fragment = InterviewTileFragment()
        fragment.setUIMode(1)
        fragmentTransaction.replace(R.id.interview_tile_fragment, fragment)
        val fragment2 = InterviewTileFragment()
        fragment2.setUIMode(2)
        fragmentTransaction.replace(R.id.interview_tile_fragment2, fragment2)
        val fragment3 = InterviewTileFragment()
        fragment3.setUIMode(3)
        fragmentTransaction.replace(R.id.interview_tile_fragment3, fragment3)
        val fragment4 = InterviewTileFragment()
        fragment4.setUIMode(4)
        fragmentTransaction.replace(R.id.interview_tile_fragment4, fragment4)
        val fragment5 = InterviewTileFragment()
        fragment5.setUIMode(5)
        fragmentTransaction.replace(R.id.interview_tile_fragment5, fragment5)
        val fragment6 = InterviewTileFragment()
        fragment6.setUIMode(6)
        fragmentTransaction.replace(R.id.interview_tile_fragment6, fragment6)
        val fragment7 = InterviewTileFragment()
        fragment7.setUIMode(7)
        fragmentTransaction.replace(R.id.interview_tile_fragment7, fragment7)
        fragmentTransaction.commit()

        initUI()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    private fun initUI() {
        val processStageBar = ProcessStageBarView(context!!, process)
        layoutStageControl.addView(processStageBar)

        for ((index, stage) in process.getStageList().withIndex()) {
            if (stage.stageStatus == Enums.StageType.CURRENT.value) {
                currentStatus = index
                break
            }
        }

        processTriangleBar = ProcessStageTriangleView(context!!, process)
        processTriangleBar.setStatus(currentStatus)
        layoutStageControl.addView(processTriangleBar)

        setTileView(currentStatus)

        imgPrev.setOnClickListener {
            if (currentStatus > 0) {
                currentStatus--
                processTriangleBar.setStatus(currentStatus)
                setTileView(currentStatus)
            }
        }

        imgNext.setOnClickListener {
            if (currentStatus < (process.getStageList().size - 1)) {
                currentStatus++
                processTriangleBar.setStatus(currentStatus)
                setTileView(currentStatus)
            }
        }
    }

    fun setTileView(status: Int) {
        val stage = process.getStageList()[status]

        txtStage.text = stage.clientTileTitle
        if (stage.clientTileText.safeValue() == "") {
            txtDescription.visibility = View.GONE
            txtAvailability.visibility = View.VISIBLE

            txtAvailability.text = stage.clientTileButton

            txtAvailability.setOnClickListener {
                when (stage.clientTileButton) {
                    Enums.TileActionButtonType.ADD_INTERVIEWERS.value -> {
                        val intent = Intent(activity, AddInterviewersActivity::class.java)
                        intent.putExtra("from", "Add")
                        intent.putExtra("processId", process.id)
                        intent.putExtra("jobId", process.jobId)
                        intent.putExtra("interviewId", process.currentInterviewId.toInt())
                        intent.putExtra("candidateName", process.candidateFullName)

                        activity!!.finish()

                        activity!!.startActivity(intent)
                    }
                    Enums.TileActionButtonType.JOIN_INTERVIEW.value -> {
                        val intent = Intent(activity, AddInterviewersActivity::class.java)
                        intent.putExtra("from", "Join")
                        intent.putExtra("processId", process.id)
                        intent.putExtra("jobId", process.jobId)
                        intent.putExtra("interviewId", process.currentInterviewId.toInt())
                        intent.putExtra("candidateName", process.candidateFullName)

                        activity!!.finish()

                        activity!!.startActivity(intent)
                    }
                }
            }
        } else {
            txtDescription.visibility = View.VISIBLE
            txtAvailability.visibility = View.GONE

            txtDescription.text = stage.clientTileText
        }

        val fontType =
            if (stage.clientTileIconStyle == Enums.FontType.REGULAR.value) Typeface.createFromAsset(
                context!!.assets,
                "fonts/fontawesome_regular_pro.otf"
            )
            else Typeface.createFromAsset(context!!.assets, "fonts/fontawesome_solid_pro.otf")

        txtStageIcon.typeface = fontType

        txtStageIcon.text = Utility.getStageClientTileIcon(stage.clientTileIconCode)
        txtStageIcon.setTextColor(Color.parseColor(Utility.getStageClientTileIconColor(stage.clientTileIconColor)))

        layoutTile.setBackgroundResource(Utility.getTileBackgroundResourceByStatus(stage.clientTileBackgroundColor))
    }
}
