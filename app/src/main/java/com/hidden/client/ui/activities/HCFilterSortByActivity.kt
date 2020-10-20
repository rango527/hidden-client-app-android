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

class HCFilterSortByActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonBack: ImageView
    private lateinit var imgTickActivity: ImageView
    private lateinit var imgTickProcess: ImageView
    private lateinit var imgTickDate: ImageView
    private lateinit var layoutFilterActivity: ConstraintLayout
    private lateinit var layoutFilterProcess: ConstraintLayout
    private lateinit var layoutFilterDate: ConstraintLayout
    private lateinit var buttonDone: Button

    private var sortBy: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_sort_by)

        buttonBack = findViewById(R.id.img_back)
        buttonBack.setOnClickListener(this)

        buttonDone = findViewById(R.id.button_filter_done)
        buttonDone.setOnClickListener(this)

        layoutFilterActivity = findViewById(R.id.layout_filter_most_recently_activity)
        layoutFilterActivity.setOnClickListener(this)

        layoutFilterProcess = findViewById(R.id.layout_filter_process_stage)
        layoutFilterProcess.setOnClickListener(this)

        layoutFilterDate = findViewById(R.id.layout_filter_shortlisting_date)
        layoutFilterDate.setOnClickListener(this)

        imgTickActivity = findViewById(R.id.img_tick_most_recent_activity)
        imgTickProcess = findViewById(R.id.img_tick_process_stage)
        imgTickDate = findViewById(R.id.img_tick_shortlisting_date)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                finish()
            }

            R.id.button_filter_done -> {
                HCGlobal.getInstance().tempProcessFilterList.currentSortBy = sortBy

                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_filter_most_recently_activity -> {
                imgTickActivity.setImageResource(R.drawable.tick_on)
                imgTickProcess.setImageResource(R.drawable.tick_off)
                imgTickDate.setImageResource(R.drawable.tick_off)
                sortBy = 0
            }

            R.id.layout_filter_process_stage -> {
                imgTickProcess.setImageResource(R.drawable.tick_on)
                imgTickDate.setImageResource(R.drawable.tick_off)
                imgTickActivity.setImageResource(R.drawable.tick_off)
                sortBy = 1
            }

            R.id.layout_filter_shortlisting_date -> {
                imgTickDate.setImageResource(R.drawable.tick_on)
                imgTickProcess.setImageResource(R.drawable.tick_off)
                imgTickActivity.setImageResource(R.drawable.tick_off)
                sortBy = 2
            }
        }
    }
}
