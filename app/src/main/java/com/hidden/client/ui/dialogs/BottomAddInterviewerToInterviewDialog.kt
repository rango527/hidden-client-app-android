package com.hidden.client.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.JobSettingActivity
import com.hidden.client.ui.activities.ProcessSettingActivity

class BottomAddInterviewerToInterviewDialog(private val processId: Int, private val jobId: Int) : DialogFragment() {

    private lateinit var txtAddToProcess: TextView
    private lateinit var txtAddToJob: TextView
    private lateinit var txtCancel: TextView

    private lateinit var layoutBlack: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_FullScreenDialog
        )
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.bottom_sheet_add_interviewer_to_interview, container, false)

        txtCancel = view.findViewById(R.id.text_cancel)
        txtAddToJob = view.findViewById(R.id.text_add_to_job)
        txtAddToProcess = view.findViewById(R.id.text_add_to_process)

        layoutBlack = view.findViewById(R.id.layout_blank)

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        txtCancel.setOnClickListener {
            dismiss()
        }

        txtAddToJob.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, JobSettingActivity::class.java)
            Log.d("txtaddjob", "txtaddtojob $jobId")
            Log.d("txtaddjob", "txtaddtojob $intent")
            intent.putExtra("jobId", jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        txtAddToProcess.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, ProcessSettingActivity::class.java)
            intent.putExtra("jobId", jobId)
            intent.putExtra("processId", processId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        layoutBlack.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "bottom_add_interviewer_to_interview_dialog"
        fun display(fragmentManager: FragmentManager?, processId: Int, jobId: Int): BottomAddInterviewerToInterviewDialog {
            val dialog = BottomAddInterviewerToInterviewDialog(processId, jobId)
            dialog.show(fragmentManager!!, TAG)
            return dialog
        }
    }
}