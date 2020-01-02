package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.hidden.client.R
import com.hidden.client.databinding.CandidateListBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.CandidateListVM
import com.kaopiz.kprogresshud.KProgressHUD

class CandidateListActivity : BaseActivity() {

    private lateinit var binding: CandidateListBinding
    private lateinit var viewModel: CandidateListVM
    private var errorSnackbar: Snackbar? = null

    private lateinit var progressDlg: KProgressHUD

    private lateinit var editSearch: EditText

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_candidate_list)
        binding.recyclerviewCandidates.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(CandidateListVM::class.java)
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
                swipeContainer.isRefreshing = false
            }
        })

        editSearch = findViewById(R.id.edit_search)
        editSearch.doAfterTextChanged { text -> viewModel.search = text }

        initCloseButton()

        swipeContainer = findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener {
            viewModel.loadCandidateList(false)
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
