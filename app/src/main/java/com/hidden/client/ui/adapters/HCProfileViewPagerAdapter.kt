package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
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
import com.hidden.client.models_.HCProfile
import com.hidden.client.ui.custom.SkillItemView
import android.widget.LinearLayout.LayoutParams
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.helpers.HCConstants
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.ui.activities.ProjectDetailActivity
import com.hidden.client.ui.activities.settings.CandidateDetailActivity
import com.hidden.client.ui.activities.settings.CandidateListActivity
import com.hidden.client.ui.activities.shortlist.ShortlistDetailActivity
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.security.acl.Owner

class HCProfileViewPagerAdapter : PagerAdapter {

    companion object {
        val default_skill_show_count = 3
    }

    var context: Context
    var profileList: MutableList<HCProfile> = mutableListOf()
    lateinit var inflater: LayoutInflater
    lateinit var fragment: Fragment

    constructor(
        context: Context,
        profileList: MutableList<HCProfile>,
        fragment: Fragment
    ) : super() {
        this.context = context
        this.profileList = profileList
        this.fragment = fragment
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return profileList.size;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.viewpager_profile_item, container, false)

        val displayMetrics = DisplayMetrics()
        HCGlobal.getInstance().currentActivity.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val layout: LinearLayout = view.findViewById(R.id.viewpager)
        layout.setOnClickListener(View.OnClickListener {
            HCGlobal.getInstance().currentIndex = position
            val intent = Intent(HCGlobal.getInstance().currentActivity, ShortlistDetailActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        })

        val lblHiddenSays: TextView = view.findViewById(R.id.lbl_hidden_says)
        val lblHasWorkWith: TextView = view.findViewById(R.id.lbl_has_work_with)
        val lblProject: TextView = view.findViewById(R.id.lbl_project)
        val lblSkills: TextView = view.findViewById(R.id.lbl_skills)

        val imgProfileLogo: CircleImageView = view.findViewById(R.id.img_profile_logo)
        val textProfileTitle: TextView = view.findViewById(R.id.text_profile_title)
        val textProfileLocations: TextView = view.findViewById(R.id.text_profile_location)
        val textProfileFeedback: TextView = view.findViewById(R.id.text_profile_feedback)

        Glide.with(context).load(profileList[position].getPhoto()).into(imgProfileLogo)
        textProfileTitle.text = profileList[position].getTitle()
        textProfileLocations.text = profileList[position].getLocation()

        textProfileFeedback.text = profileList[position].getFeedback()
        if (profileList[position].getFeedback() === "") {
            lblHiddenSays.visibility = View.GONE
            textProfileFeedback.visibility = View.GONE
        }

        var rvProfileProjects: RecyclerView = view.findViewById(R.id.recyclerview_profile_projects)

        // Job Title
        val textProfileJobTitles: TextView = view.findViewById(R.id.text_profile_job_titles)
        textProfileJobTitles.setText(profileList[position].getJobTitleWithSeparator())
        textProfileJobTitles.setTextColor(
            context.resources.getIdentifier(
                profileList[position].getAvatarColor(),
                "color",
                context!!.packageName
            )
        )

        // `Has Worked With` Image List
        val rvProfileEmployeeHistories: RecyclerView =
            view.findViewById(R.id.recyclerview_profile_employee_history)
        val layoutProfileEmployeeHistories: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvProfileEmployeeHistories.layoutManager = layoutProfileEmployeeHistories
        rvProfileEmployeeHistories.setHasFixedSize(true)
        val profileEmployeeHistoriesAdapter: HCImageAdapter = HCImageAdapter(
            profileList[position].getEmployeeHistory(),
            HCConstants.IMAGE_TYPE_CIRCLE, context
        )
        rvProfileEmployeeHistories.adapter = profileEmployeeHistoriesAdapter

        if (profileList[position].getEmployeeHistory().isEmpty()) {
            lblHasWorkWith.visibility = View.GONE
            rvProfileEmployeeHistories.visibility = View.GONE
        }

        // Portfolio RecyclerView
        val rvProfilePortfolio: RecyclerView = view.findViewById(R.id.recyclerview_profile_projects)
        val layoutProfilePortfolio: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvProfilePortfolio.layoutManager = layoutProfilePortfolio
        rvProfilePortfolio.setHasFixedSize(true)
        val profilePortfolioAdapter: HCImageAdapter = HCImageAdapter(
            profileList[position].getProjects(),
            HCConstants.IMAGE_TYPE_ROUNDED_RECTANGLE,
            true,
            position,
            context
        )
        rvProfilePortfolio.adapter = profilePortfolioAdapter

        if (profileList[position].getProjects().isEmpty()) {
            lblProject.visibility = View.GONE
            rvProfilePortfolio.visibility = View.GONE
        }

        // Skill Layout  (default limit count)
        var skillLayout: FlexboxLayout = view.findViewById(R.id.layout_skills)

        var skillCount: Int = profileList[position].getSkills().size
        var defaultShowCount =
            if (skillCount <= default_skill_show_count) skillCount else HCProfileViewPagerAdapter.default_skill_show_count

        for (i in 0 until defaultShowCount) {
            var skillItem = profileList[position].getSkills().get(i)
            var skillItemView =
                SkillItemView(context, skillItem.getSkill(), skillItem.getProficiency())
            skillLayout.addView(skillItemView)
        }

        // add `+ $(cnt) more`
        if (skillCount > default_skill_show_count) {
            var textAddMore = TextView(context)
            var params: LayoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT, // This will define text view width
                LayoutParams.MATCH_PARENT // This will define text view height
            )
            params.leftMargin = 5

            textAddMore.layoutParams = params
            textAddMore.setText(
                String.format(
                    context.resources.getString(R.string.add_more),
                    skillCount - defaultShowCount
                )
            )
            textAddMore.gravity = Gravity.CENTER_VERTICAL

            skillLayout.addView(textAddMore)
        }

        if (profileList[position].getSkills().isEmpty()) {
            lblSkills.visibility = View.GONE
            skillLayout.visibility = View.GONE
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View   )
    }

    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }
}