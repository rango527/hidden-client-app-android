package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.view.View
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.Utility
import com.hidden.client.models.ShortlistCandidateEntity
import com.hidden.client.ui.adapters.BrandListAdapter
import com.hidden.client.ui.adapters.ProjectListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM

class ShortlistViewVM(private val shortlistCandidate: ShortlistCandidateEntity) : RootVM() {

    // Job Title - Job1 | Job2 | Job3
    val jobTitle: String = Utility.makeJobString(
        shortlistCandidate.jobTitle_1,
        shortlistCandidate.jobTitle_2,
        shortlistCandidate.jobTitle_3
    )
    fun getJobTitleTextColor(context: Context): Int {
        return Utility.getResourceByName(
            context,
            Enums.Resource.COLOR.value,
            shortlistCandidate.avatarColor
        )
    }

    // SnapShot
    val snapshotVisibility: Int = if (shortlistCandidate.hiddenSays == "")
        View.GONE
    else
        View.VISIBLE

    // BrandList
    val brandListVisibility: Int = if (shortlistCandidate.getBrandList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val brandListAdapter: BrandListAdapter = BrandListAdapter()

    // ProjectList
    val projectListVisibility: Int = if (shortlistCandidate.getProjectList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val projectListAdapter: ProjectListAdapter = ProjectListAdapter()

    // Skill List
    val skillListVisibility: Int = if (shortlistCandidate.getSkillList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    init {
        brandListAdapter.updateBrandList(shortlistCandidate.getBrandList())
        projectListAdapter.updateProjectList(shortlistCandidate.getProjectList())
    }

    fun getShortlistCandidate(): ShortlistCandidateEntity {
        return shortlistCandidate
    }

}