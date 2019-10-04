package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R
import kotlinx.android.synthetic.main.activity_process_filter.view.*

class HCProcessFilterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var layoutBtnJob: LinearLayout
    private lateinit var layoutBtnStorageProcess: LinearLayout
    private lateinit var layoutBtnReadStatus: LinearLayout
    private lateinit var layoutBtnSortBy: LinearLayout

    private lateinit var textBtnJob: TextView
    private lateinit var textBtnStorageProcess: TextView
    private lateinit var textBtnReadStatus: TextView
    private lateinit var textBtnSortBy: TextView

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

        textBtnJob = findViewById(R.id.text_filter_job)
        textBtnStorageProcess = findViewById(R.id.text_filter_process_stage)
        textBtnReadStatus = findViewById(R.id.text_filter_read_status)
        textBtnSortBy = findViewById(R.id.text_filter_sort_by)

        btnViewProcess = findViewById(R.id.button_view_process)
        btnClear = findViewById(R.id.button_clear)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_job -> {

            }
            R.id.layout_filter_process_stage -> {

            }
            R.id.layout_filter_read_status -> {

            }
            R.id.layout_filter_sort_by -> {

            }
            R.id.button_view_process -> {

            }
            R.id.button_clear -> {

            }
        }
    }
}
