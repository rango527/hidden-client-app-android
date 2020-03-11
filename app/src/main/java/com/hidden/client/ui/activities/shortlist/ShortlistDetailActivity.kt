package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.databinding.ShortlistDetailViewBinding
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.Utility
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM

class ShortlistDetailActivity : BaseActivity(), View.OnClickListener {

    // RecyclerViews
    private lateinit var rvBrand: RecyclerView
    private lateinit var rvProject: RecyclerView
    private lateinit var rvWorkExperience: RecyclerView

    private lateinit var layoutSkill: FlexboxLayout

    // Background
    private lateinit var layoutBackground: ConstraintLayout

    // ViewModel
    private lateinit var binding: ShortlistDetailViewBinding
    private lateinit var viewModel: ShortlistViewVM

    // Approve & Reject
    private lateinit var layoutApprove: LinearLayout
    private lateinit var layoutReject: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HCGlobal.getInstance().shortlistCandidateVMList[HCGlobal.getInstance().currentIndex]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shortlist_detail)
        binding.viewModel = viewModel

        HCGlobal.getInstance().currentActivity = this

        initCloseButton()

        // BrandList
        rvBrand = findViewById(R.id.recyclerview_brand)
        rvBrand.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBrand.setHasFixedSize(true)

        // ProjectList (Highlights)
        rvProject = findViewById(R.id.recyclerview_project)
        rvProject.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvProject.setHasFixedSize(true)

        // Skill Layout
        layoutSkill = findViewById(R.id.layout_skills)
        for (skill in viewModel.getShortlistCandidate().getSkillList()) {
            layoutSkill.addView(SkillItemView(this, skill.name, skill.ranking))
        }

        rvWorkExperience = findViewById(R.id.recyclerview_work_experience)
        rvWorkExperience.layoutManager = LinearLayoutManager(this)
        rvWorkExperience.setHasFixedSize(true)

        layoutApprove = findViewById(R.id.layout_approve)
        layoutApprove.setOnClickListener(this)
        layoutReject = findViewById(R.id.layout_reject)
        layoutReject.setOnClickListener(this)

        layoutBackground = findViewById(R.id.layout_background)

        layoutBackground.setBackgroundResource(
            Utility.getResourceByName(
                this,
                Enums.Resource.DRAWABLE.value,
                HCGlobal.getInstance().shortlistCandidateVMList[HCGlobal.getInstance().currentIndex].getShortlistCandidate().avatarColor
            )
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_approve -> {

                val intent = Intent(this, FeedbackActivity::class.java)

                intent.putExtra("processId", viewModel.getShortlistCandidate().processId)
                intent.putExtra("avatarName", viewModel.getShortlistCandidate().avatarName)
                intent.putExtra("isApprove", true)

                startActivity(intent)
            }
            R.id.layout_reject -> {

                val intent = Intent(this, FeedbackActivity::class.java)

                intent.putExtra("processId", viewModel.getShortlistCandidate().processId)
                intent.putExtra("avatarName", viewModel.getShortlistCandidate().avatarName)
                intent.putExtra("isApprove", false)

                startActivity(intent)
            }
        }
    }
}
