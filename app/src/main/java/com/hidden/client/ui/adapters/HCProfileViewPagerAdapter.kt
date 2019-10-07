package com.hidden.client.ui.adapters

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCProfile
import com.hidden.client.models.HCSkill
import com.hidden.client.ui.custom.SkillItemView
import android.widget.LinearLayout.LayoutParams
import androidx.core.view.marginLeft
import com.google.android.flexbox.FlexboxLayout

class HCProfileViewPagerAdapter : PagerAdapter{

    companion object {
        val default_skill_show_count = 3
    }

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

        var rvProfileProjects: RecyclerView = view.findViewById(R.id.recyclerview_profile_projects)

        // Job Title
        var textProfileJobTitles: TextView = view.findViewById(R.id.text_profile_job_titles)
        textProfileJobTitles.setText(profileList[position].getJobTitleWithSeparator())

        // `Has Worked With` Image List
        var rvProfileEmployeeHistories: RecyclerView = view.findViewById(R.id.recyclerview_profile_employee_history)
        var layoutProfileEmployeeHistories: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvProfileEmployeeHistories.layoutManager = layoutProfileEmployeeHistories
        rvProfileEmployeeHistories.setHasFixedSize(true)
        var profileEmployeeHistoriesAdapter: HCImageAdapter = HCImageAdapter(profileList[position].getEmployeeHistory(),
            HCGlobal.getInstance(context).IMAGE_TYPE_CIRCLE, context)
        rvProfileEmployeeHistories.adapter = profileEmployeeHistoriesAdapter

        // Portfolio RecyclerView
        var rvProfilePortfolio: RecyclerView = view.findViewById(R.id.recyclerview_profile_projects)
        var layoutProfilePortfolio: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvProfilePortfolio.layoutManager = layoutProfilePortfolio
        rvProfilePortfolio.setHasFixedSize(true)
        var profilePortfolioAdapter: HCImageAdapter = HCImageAdapter(profileList[position].getProjects(),
            HCGlobal.getInstance(context).IMAGE_TYPE_ROUNDED_RECTANGLE, context)
        rvProfilePortfolio.adapter = profilePortfolioAdapter

        // Skill Layout
        var skillLayout: FlexboxLayout = view.findViewById(R.id.layout_skills)

        var skillCount:Int = profileList[position].getSkills().size
        var defaultShowCount = if (skillCount <= default_skill_show_count) skillCount else HCProfileViewPagerAdapter.default_skill_show_count

        for (i in 0 until defaultShowCount) {
            var skillItem = profileList[position].getSkills().get(i)
            var skillItemView = SkillItemView(context, skillItem.getSkill(), skillItem.getProficiency())
            skillLayout.addView(skillItemView)
        }

        if (skillCount > default_skill_show_count) {
            var textAddMore = TextView(context)
            var params : LayoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT, // This will define text view width
                LayoutParams.MATCH_PARENT // This will define text view height
            )
            params.leftMargin = 5

            textAddMore.layoutParams = params
            textAddMore.setText(String.format(context.resources.getString(R.string.add_more), skillCount - defaultShowCount))
            textAddMore.gravity = Gravity.CENTER_VERTICAL

            textAddMore.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    v!!.visibility = View.GONE

                    for (i in defaultShowCount until skillCount) {
                        var skillItem = profileList[position].getSkills().get(i)
                        var skillItemView = SkillItemView(context, skillItem.getSkill(), skillItem.getProficiency())
                        skillLayout.addView(skillItemView)
                    }
                }
            })
            skillLayout.addView(textAddMore)
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.83f
    }
}