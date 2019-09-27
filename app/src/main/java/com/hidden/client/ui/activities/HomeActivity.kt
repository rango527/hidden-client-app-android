package com.hidden.client.ui.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hidden.client.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each

        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_dashboard, R.id.navigation_shortlists, R.id.navigation_processes
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
//        when (menuItem.itemId) {
//            R.id.navigation_dashboard -> {
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_shortlists -> {
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_processes -> {
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
}
