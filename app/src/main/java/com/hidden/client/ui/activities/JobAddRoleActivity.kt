package com.hidden.client.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.UserManagerListBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDialog
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.CandidateListVM
import com.hidden.client.ui.viewmodels.main.JobSettingVM
import com.kaopiz.kprogresshud.KProgressHUD

class JobAddRoleActivity : AppCompatActivity() {

    private lateinit var binding: UserManagerListBinding
    private lateinit var viewModel: JobSettingVM

    private lateinit var imgClose: ImageView
    private lateinit var txtReviewType: TextView
    private lateinit var txtAddTeamMember: TextView

    private lateinit var progressDlg: KProgressHUD

    private lateinit var editSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_job_add_role)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_add_role)
        binding.recyclerviewUserManager.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(JobSettingVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
            }
        })

        val jobId: Int = intent.getIntExtra("jobId", 0)
        val reviewType: Int = intent.getIntExtra("reviewType", 0)

        viewModel.jobId = jobId

        editSearch = findViewById(R.id.edit_search)


        txtReviewType = findViewById(R.id.text_review_type)
        when (reviewType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.shortlist_reviewer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.interviewer,
                    1, 1
                )
            Enums.ReviewerType.INTERVIEWER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.interviewer_advancer,
                    1, 1
                )
            Enums.ReviewerType.OFFER_MANAGER.value -> txtReviewType.text =
                resources.getQuantityString(
                    R.plurals.offer_manager,
                    1, 1
                )
            else -> txtReviewType.text = resources.getQuantityString(
                R.plurals.shortlist_reviewer,
                1, 1
            )
        }

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }

        txtAddTeamMember = findViewById(R.id.text_add_team_member)
        txtAddTeamMember.setOnClickListener {
            val intent = Intent(this, InviteTeamMember::class.java)
            startActivity(intent)
        }
    }
}
