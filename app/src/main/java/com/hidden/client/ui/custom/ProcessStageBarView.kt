package com.hidden.client.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.LinearLayout
import com.hidden.client.R
import com.hidden.client.helpers.Utility
import com.hidden.client.models.entity.ProcessEntity

@SuppressLint("ViewConstructor")
class ProcessStageBarView (context: Context, process: ProcessEntity): LinearLayout(context) {

    init {
        inflate(context, R.layout.process_stage_bar_view, this)

        val stageList = process.getStageList()

        val initStage = stageList[0]
        val firstInterviewStage = stageList[1]
        val furtherInterviewStage = stageList[2]
        val finalInterviewStage = stageList[3]
        val offerStage = stageList[4]
        val offerAcceptedStage = stageList[5]
        val startedStage = stageList[6]

        val layoutInit: LinearLayout = findViewById(R.id.layout_stage_init)
        layoutInit.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(initStage.clientTileBackgroundColor))

        val layoutFirstInterview: LinearLayout = findViewById(R.id.layout_stage_first_interview)
        layoutFirstInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(firstInterviewStage.clientTileBackgroundColor))

        val layoutFurtherInterview: LinearLayout = findViewById(R.id.layout_stage_further_interview)
        layoutFurtherInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(furtherInterviewStage.clientTileBackgroundColor))

        val layoutFinalInterview: LinearLayout = findViewById(R.id.layout_stage_final_interview)
        layoutFinalInterview.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(finalInterviewStage.clientTileBackgroundColor))

        val layoutOfferStage: LinearLayout = findViewById(R.id.layout_stage_offer_stage)
        layoutOfferStage.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(offerStage.clientTileBackgroundColor))

        val layoutOfferAcceptedStage: LinearLayout = findViewById(R.id.layout_stage_offer_accepted_stage)
        layoutOfferAcceptedStage.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(offerAcceptedStage.clientTileBackgroundColor))

        val layoutStarted: LinearLayout = findViewById(R.id.layout_stage_started)
        layoutStarted.setBackgroundResource(Utility.getBackgroundResourceFromCurrentStatus(startedStage.clientTileBackgroundColor))
    }
}