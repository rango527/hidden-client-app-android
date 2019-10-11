package com.hidden.client.ui.fragments.home.processes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCProcess
import com.hidden.client.ui.activities.HCProcessFilterActivity
import com.hidden.client.ui.adapters.HCProcessAdapter
import com.hidden.client.ui.adapters.HCYourJobAdapter
import com.hidden.client.ui.viewmodels.HCProcessViewModel
import com.hidden.client.ui.viewmodels.HCYourJobViewModel

class HCProcessesFragment : Fragment(), View.OnClickListener {

    private lateinit var rvProcess: RecyclerView
    private lateinit var processesViewModel: HCProcessViewModel
    private var processList: MutableList<HCProcess> = mutableListOf()
    private lateinit var processAdapter: HCProcessAdapter

    private lateinit var layoutBtnFilterSearch: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home_processes, container, false)

        // Process RecyclerView
        rvProcess = root.findViewById(R.id.recyclerview_processes)
        processesViewModel = ViewModelProviders.of(this).get(HCProcessViewModel::class.java)
        processesViewModel.getProcessList().observe(this, Observer {processViewModels->
            processAdapter = HCProcessAdapter(activity!!.applicationContext, processViewModels)

            rvProcess.layoutManager = LinearLayoutManager(activity!!.applicationContext)
            rvProcess.setHasFixedSize(true)

            rvProcess.adapter = processAdapter
        })

        layoutBtnFilterSearch = root.findViewById(R.id.layout_filter_search);
        layoutBtnFilterSearch.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_search -> {
                val intent = Intent(context, HCProcessFilterActivity::class.java)
                startActivity(intent)
            }
         }
    }
}