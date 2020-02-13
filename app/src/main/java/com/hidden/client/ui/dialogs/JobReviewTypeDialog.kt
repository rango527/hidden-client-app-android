package com.hidden.client.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hidden.client.R
import com.hidden.client.helpers.Enums

class JobReviewTypeDialog(private var reviewType: Int) : DialogFragment() {

    private lateinit var imgClose: ImageView
    private lateinit var txtReviewType: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.dialog_review_type, container, false)

        imgClose = view.findViewById(R.id.img_close)
        txtReviewType = view.findViewById(R.id.text_review_type)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgClose.setOnClickListener { dismiss() }

        when (reviewType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value -> txtReviewType.text = getString(R.string.shortlist_reviewers_description)
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> txtReviewType.text = getString(R.string.interview_advancers_description)
            Enums.ReviewerType.INTERVIEWER.value -> txtReviewType.text = getString(R.string.interviewers_description)
            Enums.ReviewerType.OFFER_MANAGER.value -> txtReviewType.text = getString(R.string.offer_manager_description)
            else -> txtReviewType.text = getString(R.string.shortlist_review_type_description)
        }
    }

    companion object {

        val TAG = "job_setting_dialog"

        fun display(fragmentManager: FragmentManager, reviewType: Int): JobReviewTypeDialog {
            val jobSettingDialog = JobReviewTypeDialog(reviewType)
            jobSettingDialog.show(fragmentManager, TAG)
            return jobSettingDialog
        }
    }
}
