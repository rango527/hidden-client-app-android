package com.hidden.client.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.hidden.client.R
import com.hidden.client.databinding.JobListBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobListVM
import com.kaopiz.kprogresshud.KProgressHUD

class HCFilterJobActivity : BaseActivity(), View.OnClickListener {

    private lateinit var buttonBack: ImageView
    private lateinit var buttonDone: Button
    private lateinit var imgTickJob: ImageView

    private lateinit var binding: JobListBinding
    private lateinit var viewModel: JobListVM

    private var errorSnackbar: Snackbar? = null

    private lateinit var progressDlg: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_job)
        binding.listviewJobList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(JobListVM::class.java)

//        if (HCGlobal.getInstance().getAllJobList.size == 0) {
//            viewModel.loadJob(false)
//        }

        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })

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

        buttonBack = findViewById(R.id.img_back_job)
        buttonBack.setOnClickListener(this)

        buttonDone = findViewById(R.id.button_job_filter_done)
        buttonDone.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back_job -> {
                finish()
            }
            R.id.button_job_filter_done -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
            R.id.layout_filter_job_list -> {
                imgTickJob.setImageResource(R.drawable.tick_on)
            }
        }

    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
}
