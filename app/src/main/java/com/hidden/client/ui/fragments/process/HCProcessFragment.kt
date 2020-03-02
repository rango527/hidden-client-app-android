package com.hidden.client.ui.fragments.process


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.ui.custom.ProcessStageBarView
import com.hidden.client.ui.custom.ProcessStageTriangleView

class HCProcessFragment(private val process: ProcessEntity) : Fragment() {

    private lateinit var layoutStageControl: LinearLayout

    private lateinit var layoutTile: ConstraintLayout
    private lateinit var txtStage: TextView
    //    private lateinit var imgStage: ImageView
    private lateinit var txtStageIcon: TextView
    private lateinit var txtAvailability: TextView
    private lateinit var txtDescription: TextView

    private lateinit var imgPrev: ImageView
    private lateinit var imgNext: ImageView

    private lateinit var processTriangleBar: ProcessStageTriangleView

    private var currentStatus: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_process_process, container, false)

        layoutStageControl = view.findViewById(R.id.layout_stage_control)

        layoutTile = view.findViewById(R.id.layout_title)
        txtStage = view.findViewById(R.id.text_stage)
//        imgStage = view.findViewById(R.id.image_stage)
        txtStageIcon = view.findViewById(R.id.text_stage_icon)
        txtAvailability = view.findViewById(R.id.text_availability)
        txtDescription = view.findViewById(R.id.text_description)

        imgPrev = view.findViewById(R.id.image_prev)
        imgNext = view.findViewById(R.id.image_next)

        initUI()

        return view
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
                currentStatus--;
                processTriangleBar.setStatus(currentStatus)
                setTileView(currentStatus)
            }
        }

        imgNext.setOnClickListener {
            if (currentStatus < (process.getStageList().size - 1)) {
                currentStatus++;
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
        } else {
            txtDescription.visibility = View.VISIBLE
            txtAvailability.visibility = View.GONE

            txtDescription.text = stage.clientTileText
        }

//        imgStage.setImageResource(Utility.getStageClientTileIcon(stage.clientTileIcon))
//        val fontType = if (stage.clientTileIcon Typeface.createFromAsset(context!!.assets, "")

        val fontType =
            if (stage.clientTileIconStyle == Enums.FontType.REGULAR.value) Typeface.createFromAsset(
                context!!.assets,
                "fonts/fontawesome_regular_pro.otf"
            )
            else Typeface.createFromAsset(context!!.assets, "fonts/fontawesome_solid_pro.otf")

        txtStageIcon.setTypeface(fontType)
        txtStageIcon.text = Utility.getStageClientTileIcon(stage.clientTileIcon)
//        txtStageIcon.setTextColor(Utility.getStageClientTileIconColor(stage.clientTileIconColor))
        txtStageIcon.setTextColor(Color.parseColor(Utility.getStageClientTileIconColor(stage.clientTileIconColor)))

        layoutTile.setBackgroundResource(Utility.getTileBackgroundResourceByStatus(stage.clientTileBackgroundColor))
    }
}
