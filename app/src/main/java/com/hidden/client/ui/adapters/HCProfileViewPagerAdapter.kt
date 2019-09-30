package com.hidden.client.ui.adapters

import android.content.Context
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.models.HCProfile

class HCProfileViewPagerAdapter:PagerAdapter {

    var context: Context
    var profileList: MutableList<HCProfile> = mutableListOf()

    constructor(context: Context, profileList: MutableList<HCProfile>):super() {
        this.context = context
        this.profileList = profileList
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return true;
    }

    override fun getCount(): Int {
        return 0;
    }

}