package com.hidden.client.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.fragments.home.dashboard.HCDashboardFragment
import com.hidden.client.ui.fragments.home.processes.HCProcessesFragment
import com.hidden.client.ui.fragments.home.shortlists.HCShortlistsFragment
import kotlinx.android.synthetic.main.list_row_circle_image.*

class HCHomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tabDashboard: LinearLayout
    private lateinit var tabShortlists: LinearLayout
    private lateinit var tabProcesses: LinearLayout

    private lateinit var imgDashboard: ImageView
    private lateinit var imgShortlists: ImageView
    private lateinit var imgProcesses: ImageView

    private lateinit var textDashboard: TextView
    private lateinit var textShortlists: TextView
    private lateinit var textProcesses: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        HCGlobal.getInstance(this).g_currentActivity = this

        tabDashboard = findViewById(R.id.layout_tab_dashboard)
        tabDashboard.setOnClickListener(this)

        tabShortlists = findViewById(R.id.layout_tab_shortlists)
        tabShortlists.setOnClickListener(this)

        tabProcesses = findViewById(R.id.layout_tab_processes)
        tabProcesses.setOnClickListener(this)

        imgDashboard = findViewById(R.id.img_tab_dashboard)
        imgShortlists = findViewById(R.id.img_tab_shortlists)
        imgProcesses = findViewById(R.id.img_tab_processes)

        textDashboard = findViewById(R.id.text_tab_dashboard)
        textShortlists = findViewById(R.id.text_tab_shortlists)
        textProcesses = findViewById(R.id.text_tab_processes)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HCShortlistsFragment()).commit()

            imgShortlists.setImageResource(R.drawable.menu_shortlists_filled)
            textShortlists.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_1))
        }


    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.layout_tab_dashboard -> {

                setTabButtonDefault()

                imgDashboard.setImageResource(R.drawable.menu_dashboard)
                textDashboard.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_1))

                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCDashboardFragment()).commit()
            }
            R.id.layout_tab_shortlists -> {

                setTabButtonDefault()

                imgShortlists.setImageResource(R.drawable.menu_shortlists_filled)
                textShortlists.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_1))

                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCShortlistsFragment()).commit()
            }
            R.id.layout_tab_processes -> {

                setTabButtonDefault()

                imgProcesses.setImageResource(R.drawable.menu_processes_filled)
                textProcesses.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_1))

                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCProcessesFragment()).commit()
            }
        }
    }

    private fun setTabButtonDefault() {

        imgDashboard.setImageResource(R.drawable.menu_dashboard_empty)
        textDashboard.setTextColor(ContextCompat.getColor(this, R.color.colorGray_3))

        imgShortlists.setImageResource(R.drawable.menu_shortlists_empty)
        textShortlists.setTextColor(ContextCompat.getColor(this, R.color.colorGray_3))

        imgProcesses.setImageResource(R.drawable.menu_processes_empty)
        textProcesses.setTextColor(ContextCompat.getColor(this, R.color.colorGray_3))
    }
}
