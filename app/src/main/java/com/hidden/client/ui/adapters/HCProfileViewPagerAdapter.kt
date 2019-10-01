package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.R
import com.hidden.client.models.HCProfile

class HCProfileViewPagerAdapter:PagerAdapter {

    var context: Context
    var profileList: MutableList<HCProfile> = mutableListOf()
    lateinit var inflater: LayoutInflater

    constructor(context: Context, profileList: MutableList<HCProfile>):super() {
        this.context = context
        this.profileList = profileList
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as ConstraintLayout

    override fun getCount(): Int {
        return profileList.size;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.viewpager_profile_item, container, false)

        var imgProfileLogo: ImageView = view.findViewById(R.id.img_profile_logo)
        var textProfileTitle: TextView = view.findViewById(R.id.text_profile_title)
        var textProfileLocations: TextView = view.findViewById(R.id.text_profile_location)
        var textProfileFeedback: TextView = view.findViewById(R.id.text_profile_feedback)

        var recyclerViewProfileProjects: RecyclerView = view.findViewById(R.id.recyclerview_profile_projects)

        // Job Title
        var textProfileJobTitles: TextView = view.findViewById(R.id.text_profile_job_titles)
        textProfileJobTitles.setText(profileList[position].getJobTitleWithSeparator())

        // `Has Worked With` Image List
        var recyclerViewProfileEmployeeHistories: RecyclerView = view.findViewById(R.id.recyclerview_profile_employee_history)
        var layoutProfileEmployeeHistories: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProfileEmployeeHistories.layoutManager = layoutProfileEmployeeHistories
        recyclerViewProfileEmployeeHistories.setHasFixedSize(true)
        var profileEmployeeHistoriesAdapter: HCImageAdapter = HCImageAdapter(profileList[position].getEmployeeHistory(), context)
        recyclerViewProfileEmployeeHistories.adapter = profileEmployeeHistoriesAdapter

        container!!.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as ConstraintLayout)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.83f
    }
}