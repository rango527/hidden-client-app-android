package com.hidden.client.ui.fragments.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R

class HCDashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: HCDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dashboardViewModel =
            ViewModelProviders.of(this).get(HCDashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        return root
    }
}