package com.hidden.client.ui.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hidden.client.R
import com.hidden.client.ui.fragments.home.DashboardFragment
import com.hidden.client.ui.fragments.home.ProcessFragment
import com.hidden.client.ui.fragments.home.ShortlistFragment

class HomeTabAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DashboardFragment()
            }
            1 -> {
                ShortlistFragment()
            }
            2 -> {
                ProcessFragment()
            }
            else-> {
                ShortlistFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> Resources.getSystem().getString(R.string.dashboard)
            1 -> Resources.getSystem().getString(R.string.shortlists)
            2 -> Resources.getSystem().getString(R.string.processes)
            else -> {
                Resources.getSystem().getString(R.string.shortlists)
            }
        }
    }

}