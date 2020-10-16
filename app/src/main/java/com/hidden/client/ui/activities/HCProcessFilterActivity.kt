package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal

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
//        textBtnJob = findViewById(R.id.text_filter_job)
//        textBtnStorageProcess = findViewById(R.id.text_filter_process_stage)
//        textBtnReadStatus = findViewById(R.id.text_filter_read_status)
//        textBtnSortBy = findViewById(R.id.text_filter_sort_by)

        btnViewProcess = findViewById(R.id.button_view_process)
        btnClear = findViewById(R.id.button_clear)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_job -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCFilterJobActivity::class.java)
//                intent.putExtra("conversationId", conversationId)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.layout_filter_process_stage -> {

            }
            R.id.layout_filter_read_status -> {

            }
            R.id.layout_filter_sort_by -> {

            }
            R.id.button_view_process -> {

            }
            R.id.button_back -> {
                val intent = Intent(this, ProcessActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.button_clear -> {

            }
        }
    }
}
