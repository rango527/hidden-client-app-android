package com.hidden.client.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.UserManagerBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobUserManagerVM
import com.kaopiz.kprogresshud.KProgressHUD

class JobUserManagerActivity : BaseActivity() {

    private lateinit var binding: UserManagerBinding
    private lateinit var viewModel: JobUserManagerVM

    private lateinit var imgClose: ImageView

    private lateinit var progressDlg: KProgressHUD

    private var jobId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance().currentActivity = this

        jobId = intent.getIntExtra("jobId", 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_user_manager)
        binding.recyclerviewUserManager.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(JobUserManagerVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        viewModel.loadUserManagerList(jobId)

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }
    }
}
