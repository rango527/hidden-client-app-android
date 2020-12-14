package com.hidden.client.ui.fragments.home.processes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.kaopiz.kprogresshud.KProgressHUD

class ProcessesFragment(private val cashMode: Boolean) : Fragment(), View.OnClickListener {

    private lateinit var binding: ProcessListBinding
    private lateinit var viewModel: ProcessListVM

    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var layoutBtnFilterSearch: LinearLayout
    private lateinit var layoutBtnViewFilter: LinearLayout
    private lateinit var layoutBtnClearFilter: LinearLayout
    private lateinit var layoutBtnFilterResult: LinearLayout
    private lateinit var noProcessList: LinearLayout
    private lateinit var filterText: TextView
    private lateinit var emptyText1: TextView
    private lateinit var emptyText2: TextView
    private lateinit var buttonRefresh: Button
    private lateinit var progressDlg: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(ProcessListVM::class.java)
        viewModel.loadProcess(cashMode)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
                swipeContainer.isRefreshing = false
            }
        })

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = ProcessListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val view = binding.root

        // if filter result is true, change filter layout
        layoutBtnFilterSearch = view.findViewById(R.id.layout_filter_search)
        layoutBtnFilterResult = view.findViewById(R.id.layout_filter_search_result)

        val isJobFilter = isJobFilterResult()
        val isProcessFilter = isProcessFilterResult()
        val isSortBy = isSortByResult()

        if (isJobFilter || isProcessFilter || isSortBy) {
            layoutBtnFilterResult.visibility = View.VISIBLE
            layoutBtnFilterSearch.visibility = View.GONE
        } else {
            layoutBtnFilterResult.visibility = View.GONE
            layoutBtnFilterSearch.visibility = View.VISIBLE
        }
        // end

        // if processlist count is 0, show no_processlist layout

        noProcessList = view.findViewById(R.id.no_processlist)
        filterText = view.findViewById(R.id.filter_text)
        emptyText1 = view.findViewById(R.id.empty_list_text1)
        emptyText2 = view.findViewById(R.id.empty_list_text2)
        buttonRefresh = view.findViewById(R.id.button_refresh)
        buttonRefresh.setOnClickListener(this)
        viewModel.processListCount.observe(this, Observer { show ->
            if (show) {
                if (!isJobFilter && !isProcessFilter && !isSortBy) {
                    layoutBtnFilterResult.visibility = View.GONE
                    layoutBtnFilterSearch.visibility = View.GONE
                    emptyText1.visibility = View.VISIBLE
                    emptyText2.visibility = View.VISIBLE
                    buttonRefresh.visibility = View.VISIBLE
                } else {
                    filterText.visibility = View.VISIBLE
                }
                noProcessList.visibility = View.VISIBLE
            } else {
                noProcessList.visibility = View.GONE
            }
        })
        // end

        binding.recyclerviewProcesses.layoutManager = LinearLayoutManager(context!!)

        layoutBtnFilterSearch.setOnClickListener(this)

        layoutBtnViewFilter = view.findViewById(R.id.layout_view_filter)
        layoutBtnViewFilter.setOnClickListener(this)

        layoutBtnClearFilter = view.findViewById(R.id.layout_clear_filter)
        layoutBtnClearFilter.setOnClickListener(this)
        // refreshing
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            viewModel.loadProcess(false)
        }
        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_refresh -> {
                viewModel.loadProcess(false)
            }
            R.id.layout_filter_search -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_view_filter -> {
                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_clear_filter -> {
                HCGlobal.getInstance().currentProcessFilterList.currentReadStatus = -1
                HCGlobal.getInstance().currentProcessFilterList.currentSortBy = -1
                HCGlobal.getInstance().currentProcessFilterList.currentFinalStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentFirstStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentFurtherStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentOfferAccepted = false
                HCGlobal.getInstance().currentProcessFilterList.currentOfferStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentShortlistStage = false
                HCGlobal.getInstance().currentProcessFilterList.currentStarted = false

                HCGlobal.getInstance().tempProcessFilterList.currentReadStatus = -1
                HCGlobal.getInstance().tempProcessFilterList.currentSortBy = -1
                HCGlobal.getInstance().tempProcessFilterList.currentStarted = false
                HCGlobal.getInstance().tempProcessFilterList.currentShortlistStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentOfferStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentOfferAccepted = false
                HCGlobal.getInstance().tempProcessFilterList.currentFurtherStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentFinalStage = false
                HCGlobal.getInstance().tempProcessFilterList.currentFirstStage = false

                for (x in 0 until HCGlobal.getInstance().getAllJobList.size) {
                    HCGlobal.getInstance().getJobPick[x].jobTick = false
                    HCGlobal.getInstance().getAllJobList[x].jobTick = false
                }

                val intent = Intent(HCGlobal.getInstance().currentActivity, HomeActivity::class.java)
                intent.putExtra("num", 2)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
         }
    }

    fun isJobFilterResult(): Boolean {

        val isJobFilter = HCGlobal.getInstance().getAllJobList

        for (x in 0 until isJobFilter.size) {
            if (isJobFilter[x].jobTick) {
                return true
            }
        }

        return false
    }

    fun isProcessFilterResult(): Boolean {
        val isProcessFilter = HCGlobal.getInstance().currentProcessFilterList

        if (isProcessFilter.currentFinalStage)
            return true
        if (isProcessFilter.currentFirstStage)
            return true
        if (isProcessFilter.currentFurtherStage)
            return true
        if (isProcessFilter.currentOfferAccepted)
            return true
        if (isProcessFilter.currentOfferStage)
            return true
        if (isProcessFilter.currentShortlistStage)
            return true
        if (isProcessFilter.currentStarted)
            return true
        if (isProcessFilter.currentReadStatus != -1)
            return true

        return false
    }

    fun isSortByResult(): Boolean {
        if (HCGlobal.getInstance().currentProcessFilterList.currentSortBy != -1)
            return true

        return false
    }
}