package com.hidden.client.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R

class wProcessStageTriangleView (context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.process_stage_triangle_view, this)

        val imgInit: ImageView = findViewById(R.id.img_triangle_init)
        var imgFirstInterview: ImageView = findViewById(R.id.img_triangle_first_interview)
        var imgFurtherInterview: ImageView = findViewById(R.id.img_triangle_further_interview)
        var imgFinalInterview: ImageView = findViewById(R.id.img_triangle_final_interview)
        var imgOfferStage: ImageView = findViewById(R.id.img_triangle_offer_stage)
        var imgOfferAcceptedStage: ImageView = findViewById(R.id.img_triangle_offer_accepted_stage)
        var imgStarted: ImageView = findViewById(R.id.img_triangle_started)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProcessStageBarView)

        attributes.recycle()
    }
}