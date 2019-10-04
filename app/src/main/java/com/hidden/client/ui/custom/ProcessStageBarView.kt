package com.hidden.client.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R

class ProcessStageBarView (context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.process_stage_bar_view, this)

        val imgInit: ImageView = findViewById(R.id.img_stage_init)
        var imgFirstInterview: ImageView = findViewById(R.id.img_stage_first_interview)
        var imgFurtherInterview: ImageView = findViewById(R.id.img_stage_further_interview)
        var imgFinalInterview: ImageView = findViewById(R.id.img_stage_final_interview)
        var imgOfferStage: ImageView = findViewById(R.id.img_stage_offer_stage)
        var imgOfferAcceptedStage: ImageView = findViewById(R.id.img_stage_offer_accepted_stage)
        var imgStarted: ImageView = findViewById(R.id.img_stage_started)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CompanyDetailBadge)

        attributes.recycle()
    }
}