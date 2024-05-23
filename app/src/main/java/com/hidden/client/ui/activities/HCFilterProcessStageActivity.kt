package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.custom.ProcessFilterList

class HCFilterProcessStageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonBack: ImageView

    private lateinit var imgTickShortListStage: ImageView
    private lateinit var imgTickFirstStage: ImageView
    private lateinit var imgTickFurtherStage: ImageView
    private lateinit var imgTickFinalStage: ImageView
    private lateinit var imgTickOfferStage: ImageView
    private lateinit var imgTickOfferAccepted: ImageView
    private lateinit var imgTickStarted: ImageView

    private lateinit var layoutFilterShortlistStage: ConstraintLayout
    private lateinit var layoutFilterFirstStageInterviews: ConstraintLayout
    private lateinit var layoutFilterFurtherStageInterviews: ConstraintLayout
    private lateinit var layoutFilterFinalStageInterviews: ConstraintLayout
    private lateinit var layoutFilterOfferStage: ConstraintLayout
    private lateinit var layoutFilterOfferAccepted: ConstraintLayout
    private lateinit var layoutFilterStarted: ConstraintLayout
    private lateinit var buttonDone: Button

    private var tempShortListStage: Boolean = false
    private var tempFirstStage: Boolean = false
    private var tempFurtherStage: Boolean = false
    private var tempFinalStage: Boolean = false
    private var tempOfferStage: Boolean = false
    private var tempOfferAccepted: Boolean = false
    private var tempStarted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_process_stage)

        buttonBack = findViewById(R.id.img_back)
        buttonBack.setOnClickListener(this)

        buttonDone = findViewById(R.id.button_filter_done)
        buttonDone.setOnClickListener(this)

        layoutFilterShortlistStage = findViewById(R.id.layout_filter_shortlist_stage)
        layoutFilterShortlistStage.setOnClickListener(this)

        layoutFilterFirstStageInterviews = findViewById(R.id.layout_filter_first_stage_interview)
        layoutFilterFirstStageInterviews.setOnClickListener(this)

        layoutFilterFurtherStageInterviews = findViewById(R.id.layout_filter_further_stage_interviews)
        layoutFilterFurtherStageInterviews.setOnClickListener(this)

        layoutFilterFinalStageInterviews = findViewById(R.id.layout_filter_final_stage_interviews)
        layoutFilterFinalStageInterviews.setOnClickListener(this)

        layoutFilterOfferStage = findViewById(R.id.layout_filter_offer_stage)
        layoutFilterOfferStage.setOnClickListener(this)

        layoutFilterOfferAccepted = findViewById(R.id.layout_filter_offer_accepted)
        layoutFilterOfferAccepted.setOnClickListener(this)

        layoutFilterStarted = findViewById(R.id.layout_filter_started)
        layoutFilterStarted.setOnClickListener(this)

        imgTickShortListStage = findViewById(R.id.img_tick_shortlist_stage)
        imgTickFirstStage = findViewById(R.id.img_tick_first_stage)
        imgTickFurtherStage = findViewById(R.id.img_tick_further_stage)
        imgTickFinalStage = findViewById(R.id.img_tick_final_stage)
        imgTickOfferStage = findViewById(R.id.img_tick_offer_stage)
        imgTickOfferAccepted = findViewById(R.id.img_tick_offer_accepted)
        imgTickStarted = findViewById(R.id.img_tick_started)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                finish()
            }

            R.id.button_filter_done -> {
                HCGlobal.getInstance().tempProcessFilterList.currentShortlistStage = tempShortListStage
                HCGlobal.getInstance().tempProcessFilterList.currentFinalStage = tempFinalStage
                HCGlobal.getInstance().tempProcessFilterList.currentFirstStage = tempFirstStage
                HCGlobal.getInstance().tempProcessFilterList.currentFurtherStage = tempFurtherStage
                HCGlobal.getInstance().tempProcessFilterList.currentOfferAccepted = tempOfferAccepted
                HCGlobal.getInstance().tempProcessFilterList.currentOfferStage = tempOfferStage
                HCGlobal.getInstance().tempProcessFilterList.currentStarted = tempStarted

                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_filter_shortlist_stage -> {
                if (tempShortListStage)
                    imgTickShortListStage.setImageResource(R.drawable.tick_off)
                else
                    imgTickShortListStage.setImageResource(R.drawable.tick_on)

                tempShortListStage = !tempShortListStage
            }

            R.id.layout_filter_first_stage_interview -> {
                if (tempFirstStage)
                    imgTickFirstStage.setImageResource(R.drawable.tick_off)
                else
                    imgTickFirstStage.setImageResource(R.drawable.tick_on)

                tempFirstStage = !tempFirstStage
            }

            R.id.layout_filter_further_stage_interviews -> {
                if (tempFurtherStage)
                    imgTickFurtherStage.setImageResource(R.drawable.tick_off)
                else
                    imgTickFurtherStage.setImageResource(R.drawable.tick_on)

                tempFurtherStage = !tempFurtherStage
            }

            R.id.layout_filter_final_stage_interviews -> {
                if (tempFinalStage)
                    imgTickFinalStage.setImageResource(R.drawable.tick_off)
                else
                    imgTickFinalStage.setImageResource(R.drawable.tick_on)

                tempFinalStage = !tempFinalStage
            }

            R.id.layout_filter_offer_stage -> {
                if (tempOfferStage)
                    imgTickOfferStage.setImageResource(R.drawable.tick_off)
                else
                    imgTickOfferStage.setImageResource(R.drawable.tick_on)

                tempOfferStage = !tempOfferStage
            }

            R.id.layout_filter_offer_accepted -> {
                if (tempOfferAccepted)
                    imgTickOfferAccepted.setImageResource(R.drawable.tick_off)
                else
                    imgTickOfferAccepted.setImageResource(R.drawable.tick_on)

                tempOfferAccepted = !tempOfferAccepted
            }

            R.id.layout_filter_started -> {
                if (tempStarted)
                    imgTickStarted.setImageResource(R.drawable.tick_off)
                else
                    imgTickStarted.setImageResource(R.drawable.tick_on)

                tempStarted = !tempStarted
            }
        }
    }

}
