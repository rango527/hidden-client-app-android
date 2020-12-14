package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.databinding.ShortlistItemViewBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.UI
import com.hidden.client.ui.activities.shortlist.ShortlistDetailActivity
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM
import kotlinx.android.synthetic.main.viewpager_candidate_item.view.*

class ShortlistViewPagerAdapter(
    private val context: Context,
    private val candidateVMList: List<ShortlistViewVM>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return candidateVMList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding: ShortlistItemViewBinding = ShortlistItemViewBinding.inflate(inflater, container, false)
        binding.viewModel = candidateVMList[position]

        val view = binding.root

        val layout: LinearLayout = view.findViewById(R.id.viewpager)
        layout.setOnClickListener {
            HCGlobal.getInstance().currentIndex = position
            val intent = Intent(HCGlobal.getInstance().currentActivity, ShortlistDetailActivity::class.java)
            HCGlobal.getInstance().currentActivity.overridePendingTransition(R.anim.ua_iam_fade_out, R.anim.ua_iam_fade_in)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }

        // BrandList View
        view.recyclerview_brand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.recyclerview_brand.setHasFixedSize(true)

        // ProjectList View
        view.recyclerview_projects.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.recyclerview_projects.setHasFixedSize(true)

        // Skill Layout  (default limit count)
        val skillLayout: FlexboxLayout = view.findViewById(R.id.layout_skills)

        val skillCount: Int = candidateVMList[position].getShortlistCandidate().getSkillList().size
        val defaultShowCount =
            if (skillCount <= UI.defaultSkillItemViewCount) skillCount else UI.defaultSkillItemViewCount

        for (i in 0 until defaultShowCount) {
            val skillItem = candidateVMList[position].getShortlistCandidate().getSkillList()[i]
            val skillItemView =
                SkillItemView(context, skillItem.name, skillItem.ranking)
            skillLayout.addView(skillItemView)
        }

        // add `+ $(cnt) more`
        if (skillCount > UI.defaultSkillItemViewCount) {
            val textAddMore = TextView(context)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // This will define text view width
                LinearLayout.LayoutParams.MATCH_PARENT // This will define text view height
            )
            params.leftMargin = 5

            textAddMore.layoutParams = params
            textAddMore.text = String.format(
                context.resources.getString(R.string.add_more),
                skillCount - defaultShowCount
            )
            textAddMore.gravity = Gravity.CENTER_VERTICAL

            skillLayout.addView(textAddMore)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }
}