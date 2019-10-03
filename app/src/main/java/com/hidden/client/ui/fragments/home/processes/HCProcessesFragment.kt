package com.hidden.client.ui.fragments.home.processes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCProcess
import com.hidden.client.ui.adapters.HCProcessAdapter

class HCProcessesFragment : Fragment() {

    private lateinit var processesViewModel: HCProcessesViewModel

    private lateinit var rvProcess: RecyclerView
    private var processList: MutableList<HCProcess> = mutableListOf()
    private lateinit var processAdapter: HCProcessAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        processesViewModel =
            ViewModelProviders.of(this).get(HCProcessesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_processes, container, false)

        //Your Jobs
        rvProcess = root.findViewById(R.id.recyclerview_processes)
        rvProcess.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        processList.add(
            HCProcess(R.drawable.man, "Tanya Walters",
            "Account Director", "New York, NY", 0, 0)
        )
        processList.add(HCProcess(R.drawable.water, "Sofia Bell",
            "Software Developer", "London", 0, 0))
        processList.add(HCProcess(R.drawable.coca, "Andrea Robinson",
            "CTO", "San Francisco", 0, 0))
        processList.add(HCProcess(R.drawable.coca, "Guy Guy",
            "Recuriter", "Tokyo", 0, 0))
        processList.add(HCProcess(R.drawable.man, "Jim Rose",
            "DevOps Engineer", "Hamburg", 0, 0))
        processList.add(HCProcess(R.drawable.man, "Tanya Walters",
            "Account Director", "New York, NY", 0, 0))

        processAdapter = HCProcessAdapter(processList, activity!!.applicationContext)
        rvProcess.adapter = processAdapter

        return root
    }
}