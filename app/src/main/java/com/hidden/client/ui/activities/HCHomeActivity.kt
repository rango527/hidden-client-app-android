package com.hidden.client.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.custom.HCTabBar
import com.hidden.client.ui.fragments.home.dashboard.HCDashboardFragment
import com.hidden.client.ui.fragments.home.processes.HCProcessesFragment
import com.hidden.client.ui.fragments.home.shortlists.HCShortlistsFragment
import java.lang.Exception

class HCHomeActivity : BaseActivity(), HCTabBar.OnTabSelectedListener {

    private var dashboardFrags: MutableList<Fragment> = mutableListOf<Fragment>()
    private var shortlistsFrags: MutableList<Fragment> = mutableListOf<Fragment>()
    private var processesFrags: MutableList<Fragment> = mutableListOf<Fragment>()
    private lateinit var curTabFrags: MutableList<Fragment>

    private lateinit var tabBar: HCTabBar

    private lateinit var layoutMask: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dashboardFrags.add(HCDashboardFragment())
        shortlistsFrags.add(HCShortlistsFragment())
        processesFrags.add(HCProcessesFragment())

        tabBar = findViewById(R.id.nav_view)
        tabBar.setSelectedListener(this)

        onTabSelected(1)    // default tab is Shortlists

        layoutMask = findViewById(R.id.layout_mask)
    }

    override fun onResume() {
        super.onResume()

        HCGlobal.getInstance().currentActivity = this
    }
    override fun onTabSelected(num: Int) {
        var fragsList: Array<MutableList<Fragment>> = arrayOf(dashboardFrags, shortlistsFrags, processesFrags)
        curTabFrags = fragsList[num]
        val newFrag: Fragment = curTabFrags[curTabFrags.size - 1]
        replaceFrag(newFrag)
    }

    fun replaceFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, frag).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!popFragment())
            finish();
    }

    fun pushFragment(fragment: Fragment) {

        curTabFrags.add(fragment)

        var curFrag: Fragment = curTabFrags[curTabFrags.size - 2]

        try {
            supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun popFragment(): Boolean {
        if (curTabFrags.size <= 1)
            return false

        curTabFrags.removeAt(curTabFrags.size - 1)
        var newFrag: Fragment = curTabFrags[curTabFrags.size - 1]

        try {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, newFrag).commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    fun popToRootofShortlists() {
        if (shortlistsFrags.size <= 1)
            return
        for (i in shortlistsFrags.size - 1 .. 1 ) {
            shortlistsFrags.removeAt(i)
        }
    }

    public fun toggleMask() {
        if (layoutMask.visibility == View.GONE) {
            layoutMask.visibility = View.VISIBLE
        } else {
            layoutMask.visibility = View.GONE
        }
    }
}
