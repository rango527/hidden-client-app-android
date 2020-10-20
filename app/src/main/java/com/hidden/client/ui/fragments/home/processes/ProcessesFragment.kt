package com.hidden.client.ui.fragments.home.processes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hidden.client.R
import com.hidden.client.databinding.ProcessListBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.TestMapsActivity
import com.hidden.client.ui.activities.ConversationFileAttachActivity
import com.hidden.client.ui.activities.HCProcessFilterActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ProcessListVM
import com.kaopiz.kprogresshud.KProgressHUD

class ProcessesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: ProcessListBinding
    private lateinit var viewModel: ProcessListVM

    private lateinit var layoutBtnFilterSearch: LinearLayout
    private lateinit var layoutBtnViewFilter: LinearLayout
    private lateinit var layoutBtnClearFilter: LinearLayout
    private lateinit var layoutBtnFilterResult: LinearLayout
    private lateinit var noProcessList: LinearLayout

    private lateinit var progressDlg: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(ProcessListVM::class.java)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
//                swipeContainer.isRefreshing = false
            }
        })

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = ProcessListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        val view = binding.root

        noProcessList = view.findViewById(R.id.no_processlist)
        layoutBtnFilterSearch = view.findViewById(R.id.layout_filter_search)
        layoutBtnFilterResult = view.findViewById(R.id.layout_filter_search_result)

        viewModel.processListCount.observe(this, Observer { show ->
            if (show) {
                noProcessList.visibility = View.VISIBLE
                layoutBtnFilterResult.visibility = View.VISIBLE
            } else {
                noProcessList.visibility = View.GONE
                layoutBtnFilterSearch.visibility = View.VISIBLE
            }
        })

        binding.recyclerviewProcesses.layoutManager = LinearLayoutManager(context!!)

        layoutBtnFilterSearch.setOnClickListener(this)

        layoutBtnViewFilter = view.findViewById(R.id.layout_view_filter)
        layoutBtnViewFilter.setOnClickListener(this)

        layoutBtnClearFilter = view.findViewById(R.id.layout_clear_filter)
        layoutBtnClearFilter.setOnClickListener(this)

//        swipeContainer = view.findViewById(R.id.swipeContainer)
//        swipeContainer.setOnRefreshListener {
//            viewModel.loadProcess(false)
//        }

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_search -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
//                intent.putExtra("conversationId", conversationId)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_view_filter -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_clear_filter -> {
                HCGlobal.getInstance().currentProcessFilterList.currentReadStatus = -1
                HCGlobal.getInstance().currentProcessFilterList.currentFinalStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentFirstStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentFurtherStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentOfferAccepted = false
                HCGlobal.getInstance().currentProcessFilterList.currentOfferStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentShortlistStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentStarted = false
                HCGlobal.getInstance().currentProcessFilterList.currentSortBy = -1

                val intent = Intent(HCGlobal.getInstance().currentActivity, HomeActivity::class.java)
                intent.putExtra("num", 2)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
         }
    }
}