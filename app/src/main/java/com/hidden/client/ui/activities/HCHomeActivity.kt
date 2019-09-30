package com.hidden.client.ui.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hidden.client.R
import com.hidden.client.ui.fragments.home.dashboard.HCDashboardFragment
import com.hidden.client.ui.fragments.home.processes.HCProcessesFragment
import com.hidden.client.ui.fragments.home.shortlists.HCShortlistsFragment

class HCHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_shortlists
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCShortlistsFragment()).commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCDashboardFragment()).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shortlists -> {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCShortlistsFragment()).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_processes -> {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCProcessesFragment()).commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
