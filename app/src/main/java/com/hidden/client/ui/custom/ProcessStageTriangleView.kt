package com.hidden.client.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.hidden.client.R
import com.hidden.client.helpers.Utility
import com.hidden.client.models.entity.ProcessEntity

@SuppressLint("ViewConstructor")
class ProcessStageTriangleView(context: Context, private val process: ProcessEntity) :
    LinearLayout(context) {

    private var imgInit: ImageView
    private var imgFirstInterview: ImageView
    private var imgFurtherInterview: ImageView
    private var imgFinalInterview: ImageView
    private var imgOfferStage: ImageView
    private var imgOfferAcceptedStage: ImageView
    private var imgStarted: ImageView

    init {
        inflate(context, R.layout.process_stage_triangle_view, this)

        imgInit = findViewById(R.id.img_triangle_init)
        imgFirstInterview = findViewById(R.id.img_triangle_first_interview)
        imgFurtherInterview = findViewById(R.id.img_triangle_further_interview)
        imgFinalInterview = findViewById(R.id.img_triangle_final_interview)
        imgOfferStage = findViewById(R.id.img_triangle_offer_stage)
        imgOfferAcceptedStage = findViewById(R.id.img_triangle_offer_accepted_stage)
        imgStarted = findViewById(R.id.img_triangle_started)
    }

    fun setStatus(status: Int) {
        imgInit.setImageResource(
            if (status == 0) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[0].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgFirstInterview.setImageResource(
            if (status == 1) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[1].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgFurtherInterview.setImageResource(
            if (status == 2) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[2].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgFinalInterview.setImageResource(
            if (status == 3) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[3].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgOfferStage.setImageResource(
            if (status == 4) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[4].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgOfferAcceptedStage.setImageResource(
            if (status == 5) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[5].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )

        imgStarted.setImageResource(
            if (status == 6) Utility.getTriangleBackgroundResourceByStatus(
                process.getStageList()[6].clientTileBackgroundColor
            ) else R.drawable.panel_triangle_transparent
        )
    }
}