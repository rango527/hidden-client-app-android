package com.hidden.client.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.JobSettingBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.JobAddRoleActivity
import com.hidden.client.ui.activities.JobReviewerType
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobSettingVM
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.dialog_job_settings.view.*

class JobSettingDialog(private var jobId: Int) : DialogFragment() {

    private lateinit var imgClose: ImageView

    private lateinit var imgShortlistReviewerTypeDescription: ImageView
    private lateinit var imgShortlistReviewerDescription: ImageView
    private lateinit var imgInterviewersDescription: ImageView
    private lateinit var imgInterviewAdvancerDescription: ImageView
    private lateinit var imgOfferManagerDescription: ImageView

    private lateinit var imgAddRoleShortlistReviewer: ImageView
    private lateinit var imgAddRoleInterviewer: ImageView
    private lateinit var imgAddRoleInterviewAdvancer: ImageView
    private lateinit var imgAddRoleOfferManager: ImageView

    private lateinit var viewModel: JobSettingVM

    private lateinit var progressDlg: KProgressHUD

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

        val binding: JobSettingBinding = JobSettingBinding.inflate(inflater, container, false)

        val view = binding.root

        view.recyclerview_shortlist_reviewer.layoutManager = LinearLayoutManager(inflater.context)
        view.recyclerview_interviewer.layoutManager = LinearLayoutManager(inflater.context, LinearLayoutManager.VERTICAL, false)
        view.recyclerview_interview_advancer.layoutManager = LinearLayoutManager(inflater.context, LinearLayoutManager.VERTICAL, false)
        view.recyclerview_offer_manager.layoutManager = LinearLayoutManager(inflater.context, LinearLayoutManager.VERTICAL, false)

        progressDlg = HCDialog.KProgressDialog(HCGlobal.getInstance().currentActivity)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(inflater.context)).get(JobSettingVM::class.java)
        binding.viewModel = viewModel

        viewModel.jobId = this.jobId

        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
            }
        })

        imgClose = view.findViewById(R.id.img_close)

        imgShortlistReviewerTypeDescription = view.findViewById(R.id.img_shortlist_review_text_tip)
        imgShortlistReviewerDescription = view.findViewById(R.id.img_shortlist_reviewer_tip)
        imgInterviewersDescription = view.findViewById(R.id.img_interviewer_tip)
        imgInterviewAdvancerDescription = view.findViewById(R.id.img_interview_advancer_tip)
        imgOfferManagerDescription = view.findViewById(R.id.img_offer_manager_tip)

        imgAddRoleShortlistReviewer = view.findViewById(R.id.img_add_role_to_shortlist_reviewer)
        imgAddRoleInterviewer = view.findViewById(R.id.img_add_role_to_interviewer)
        imgAddRoleInterviewAdvancer = view.findViewById(R.id.img_add_role_to_interview_advancer)
        imgAddRoleOfferManager = view.findViewById(R.id.img_add_role_to_offer_manager)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgClose.setOnClickListener { dismiss() }

        // Description
        imgShortlistReviewerTypeDescription.setOnClickListener{
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerType::class.java)
            intent.putExtra("reviewType", 0)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgShortlistReviewerDescription.setOnClickListener{
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerType::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.SHORTLIST_REVIEWER.value)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgInterviewersDescription.setOnClickListener{
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerType::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER.value)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgInterviewAdvancerDescription.setOnClickListener{
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerType::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER_ADVANCER.value)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgOfferManagerDescription.setOnClickListener{
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobReviewerType::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.OFFER_MANAGER.value)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        // Add Role
        imgAddRoleShortlistReviewer.setOnClickListener {
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobAddRoleActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.SHORTLIST_REVIEWER.value)
            intent.putExtra("jobId", jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgAddRoleInterviewer.setOnClickListener {
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobAddRoleActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER.value)
            intent.putExtra("jobId", jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgAddRoleInterviewAdvancer.setOnClickListener {
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobAddRoleActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.INTERVIEWER_ADVANCER.value)
            intent.putExtra("jobId", jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        imgAddRoleOfferManager.setOnClickListener {
            val intent: Intent = Intent(HCGlobal.getInstance().currentActivity, JobAddRoleActivity::class.java)
            intent.putExtra("reviewType", Enums.ReviewerType.OFFER_MANAGER.value)
            intent.putExtra("jobId", jobId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    companion object {

        val TAG = "job_setting_dialog"

        fun display(fragmentManager: FragmentManager, jobId: Int): JobSettingDialog {
            val jobSettingDialog = JobSettingDialog(jobId)
            jobSettingDialog.show(fragmentManager, TAG)
            return jobSettingDialog
        }
    }
}
