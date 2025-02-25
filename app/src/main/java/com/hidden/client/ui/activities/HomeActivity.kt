package com.hidden.client.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.hidden.client.R
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.custom.HCTabBar
import com.hidden.client.ui.fragments.home.dashboard.DashboardFragment
import com.hidden.client.ui.fragments.home.processes.ProcessesFragment
import com.hidden.client.ui.fragments.home.shortlists.ShortlistsFragment
import com.urbanairship.UAirship
import java.lang.Exception

class HomeActivity : BaseActivity(), HCTabBar.OnTabSelectedListener {

    private var dashboardFrags: MutableList<Fragment> = mutableListOf()
    private var shortlistsFrags: MutableList<Fragment> = mutableListOf()
    private var processesFrags: MutableList<Fragment> = mutableListOf()
    private lateinit var curTabFrags: MutableList<Fragment>

    private lateinit var tabBar: HCTabBar

    private lateinit var layoutMask: LinearLayout

    private var shortlistCashMode: Boolean = true
    private var processCashMode: Boolean = true
    private var num: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        shortlistCashMode = intent.getBooleanExtra("shortlistCashMode", true)
        processCashMode = intent.getBooleanExtra("processCashMode", true)

        val pushId = """client-${AppPreferences.myId}"""
        if (UAirship.shared().namedUser.id != pushId) {
            UAirship.shared().namedUser.id = """client-${AppPreferences.myId}"""
        }

        HCGlobal.getInstance().currentIndex = 0

        dashboardFrags.add(DashboardFragment())
        shortlistsFrags.add(ShortlistsFragment(this, shortlistCashMode))
        processesFrags.add(ProcessesFragment(processCashMode))

        shortlistCashMode = true

        tabBar = findViewById(R.id.nav_view)
        tabBar.setSelectedListener(this)

        num = intent.getIntExtra("num", 1)
        if (num == 2) {
            tabBar.processTag(num)
        }
        onTabSelected(num)    // default tab is Shortlists

        layoutMask = findViewById(R.id.layout_mask)
    }

    override fun onResume() {
        super.onResume()

        HCGlobal.getInstance().currentActivity = this
    }
    override fun onTabSelected(num: Int) {
        val fragsList: Array<MutableList<Fragment>> = arrayOf(dashboardFrags, shortlistsFrags, processesFrags)
        curTabFrags = fragsList[num]
        val newFrag: Fragment = curTabFrags[curTabFrags.size - 1]
        replaceFrag(newFrag)
    }

    private fun replaceFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, frag).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!popFragment())
            finish()
    }

    private fun popFragment(): Boolean {
        if (curTabFrags.size <= 1)
            return false

        curTabFrags.removeAt(curTabFrags.size - 1)
        val newFrag: Fragment = curTabFrags[curTabFrags.size - 1]

        try {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, newFrag).commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    fun toggleMask() {
        if (layoutMask.visibility == View.GONE) {
            layoutMask.visibility = View.VISIBLE
        } else {
            layoutMask.visibility = View.GONE
        }
    }
}
