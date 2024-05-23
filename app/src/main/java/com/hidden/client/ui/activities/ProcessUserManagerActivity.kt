package com.hidden.client.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.ProcessUserManagerBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessUserManagerVM
import com.kaopiz.kprogresshud.KProgressHUD

class ProcessUserManagerActivity : AppCompatActivity() {

    private lateinit var binding: ProcessUserManagerBinding
    private lateinit var viewModel: ProcessUserManagerVM

    private lateinit var imgClose: ImageView

    private lateinit var progressDlg: KProgressHUD

    private var processId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance().currentActivity = this

        processId = intent.getIntExtra("processId", 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_process_user_manager)
        binding.recyclerviewUserManager.layoutManager = LinearLayoutManager(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(ProcessUserManagerVM::class.java)

        binding.viewModel = viewModel

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
            }
        })

        viewModel.loadUserManagerList(processId)

        imgClose = findViewById(R.id.image_close)
        imgClose.setOnClickListener { finish() }
    }
}
