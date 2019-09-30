package com.hidden.client.ui.fragments.home.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HCHomeActivity
import com.hidden.client.ui.fragments.settings.HCSettingsFragment

class HCDashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var dashboardViewModel: HCDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dashboardViewModel =
            ViewModelProviders.of(this).get(HCDashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)

        val imgSetting: ImageView = root.findViewById(R.id.img_settings)
        imgSetting.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_settings -> {

                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCSettingsFragment()).commit()
            }
        }
    }
}