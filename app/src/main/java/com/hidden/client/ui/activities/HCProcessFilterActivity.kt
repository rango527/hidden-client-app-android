package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.fragments.home.processes.ProcessesFragment

class HCProcessFilterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var layoutBtnJob: LinearLayout
    private lateinit var layoutBtnStorageProcess: LinearLayout
    private lateinit var layoutBtnReadStatus: LinearLayout
    private lateinit var layoutBtnSortBy: LinearLayout

    private lateinit var textBtnJob: TextView
    private lateinit var textBtnStorageProcess: TextView
    private lateinit var textBtnReadStatus: TextView
    private lateinit var textBtnSortBy: TextView

    private lateinit var buttonBack: ImageView
    private lateinit var btnViewProcess: Button
    private lateinit var btnClear: Button

//    private var readStatus: Int = HCGlobal.getInstance().currentReadStatus
    private var readStatus: Int = HCGlobal.getInstance().tempProcessFilterList.currentReadStatus
    private var tempSortActivity: Int = HCGlobal.getInstance().tempProcessFilterList.currentSortBy

    private var tempShortListStage: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentShortlistStage
    private var tempFirstStage: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentFirstStage
    private var tempFurtherStage: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentFurtherStage
    private var tempFinalStage: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentFinalStage
    private var tempOfferStage: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentOfferStage
    private var tempOfferAccepted: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentOfferAccepted
    private var tempStarted: Boolean = HCGlobal.getInstance().tempProcessFilterList.currentStarted

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_filter)

        layoutBtnJob = findViewById(R.id.layout_filter_job)
        layoutBtnJob.setOnClickListener(this)

        layoutBtnStorageProcess = findViewById(R.id.layout_filter_process_stage)
        layoutBtnStorageProcess.setOnClickListener(this)

        layoutBtnReadStatus = findViewById(R.id.layout_filter_read_status)
        layoutBtnReadStatus.setOnClickListener(this)

        layoutBtnSortBy = findViewById(R.id.layout_filter_sort_by)
        layoutBtnSortBy.setOnClickListener(this)

        buttonBack = findViewById(R.id.img_back)
        buttonBack.setOnClickListener(this)

        textBtnJob = findViewById(R.id.text_filter_job)
        val tempJobFilter = HCGlobal.getInstance().getJobPick
        val currentJobFilter = HCGlobal.getInstance().getAllJobList
        for (x in 0 until currentJobFilter.size) {
            if (tempJobFilter[x].jobTick) {
                textBtnJob.text = currentJobFilter[x].jobTitle + ',' + ' ' + currentJobFilter[x].jobCityName
                break
            }
        }

        textBtnStorageProcess = findViewById(R.id.text_filter_process_stage)
        if (tempShortListStage) {
            textBtnStorageProcess.setText(R.string.shortlist_stage)
        } else if (tempFirstStage) {
            textBtnStorageProcess.setText(R.string.first_stage_interview)
        }else if (tempFurtherStage) {
            textBtnStorageProcess.setText(R.string.further_stage_interviews)
        }else if (tempFinalStage) {
            textBtnStorageProcess.setText(R.string.final_stage_interviews)
        }else if (tempOfferStage) {
            textBtnStorageProcess.setText(R.string.offer_stage)
        }else if (tempOfferAccepted) {
            textBtnStorageProcess.setText(R.string.offer_accepted)
        }else if (tempStarted) {
            textBtnStorageProcess.setText(R.string.started)
        }

        textBtnReadStatus = findViewById(R.id.text_filter_read_status)
        when (readStatus) {
            -1 -> {
                textBtnReadStatus.setText(R.string.all)
            }
            0 -> {
                textBtnReadStatus.setText(R.string.has_no_unread_messages)
            }
            1 -> {
                textBtnReadStatus.setText(R.string.has_unread_messages)
            }
        }

        textBtnSortBy = findViewById(R.id.text_filter_sort_by)
        when (tempSortActivity) {
            -1 -> {
                textBtnSortBy.setText(R.string.all)
            }
            0 -> {
                textBtnSortBy.setText(R.string.most_recent_activity)
            }
            1 -> {
                textBtnSortBy.setText(R.string.process_stage)
            }
            2 -> {
                textBtnSortBy.setText(R.string.shortlisting_date)
            }
        }

        btnViewProcess = findViewById(R.id.button_view_process)
        btnViewProcess.setOnClickListener(this)
        btnClear = findViewById(R.id.button_clear)
        btnClear.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_job -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCFilterJobActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.layout_filter_process_stage -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCFilterProcessStageActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.layout_filter_read_status -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCFilterReadStatusActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.layout_filter_sort_by -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCFilterSortByActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.button_view_process -> {
                HCGlobal.getInstance().currentProcessFilterList = HCGlobal.getInstance().tempProcessFilterList
                for (x in 0 until HCGlobal.getInstance().getAllJobList.size) {
                    HCGlobal.getInstance().getAllJobList[x].jobTick = HCGlobal.getInstance().getJobPick[x].jobTick
                }

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("num", 2)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.img_back -> {
                HCGlobal.getInstance().tempProcessFilterList = HCGlobal.getInstance().currentProcessFilterList
                for (x in 0 until HCGlobal.getInstance().getAllJobList.size) {
                    HCGlobal.getInstance().getJobPick[x].jobTick = HCGlobal.getInstance().getAllJobList[x].jobTick
                }

                finish()
            }

            R.id.button_clear -> {
                HCGlobal.getInstance().tempProcessFilterList.currentStarted = false
                HCGlobal.getInstance().tempProcessFilterList.currentShortlistStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentOfferStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentOfferAccepted = false
                HCGlobal.getInstance().tempProcessFilterList.currentFurtherStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentFinalStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentFirstStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentReadStatus = -1
                HCGlobal.getInstance().tempProcessFilterList.currentSortBy = -1

                for (x in 0 until HCGlobal.getInstance().getAllJobList.size) {
                    HCGlobal.getInstance().getJobPick[x].jobTick = false
                }

                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
                finish()
            }
        }
    }
}
