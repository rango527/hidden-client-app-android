package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.view.View
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ShortlistCandidateEntity
import com.hidden.client.ui.adapters.*
import com.hidden.client.ui.viewmodels.root.RootVM

class ShortlistViewVM(private val shortlistCandidate: ShortlistCandidateEntity) : RootVM() {

    /**
     * Job Title - Job1 | Job2 | Job3
     */
    val jobTitle: String = Utility.makeJobString(
        shortlistCandidate.jobTitle_1,
        shortlistCandidate.jobTitle_2,
        shortlistCandidate.jobTitle_3
    )

    fun getJobTitleTextColor(context: Context): Int {
        return if (shortlistCandidate.avatarColor.safeValue() != "")
            Utility.getResourceByName(
                context,
                Enums.Resource.COLOR.value,
                shortlistCandidate.avatarColor
            ) else R.color.blue
    }

    /**
     * SnapShot
     */
    val snapshotVisibility: Int = if (shortlistCandidate.hiddenSays == "")
        View.GONE
    else
        View.VISIBLE

    /**
     * BrandList
     */
    val brandListVisibility: Int = if (shortlistCandidate.getBrandList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val brandListAdapter: BrandListAdapter = BrandListAdapter()
    val brandListLgAdapter: BrandListLgAdapter = BrandListLgAdapter()

    /**
     * ProjectList
     */
    val projectListVisibility: Int = if (shortlistCandidate.getProjectList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val projectListAdapter: ProjectListAdapter = ProjectListAdapter()
    val projectListLgAdapter: ProjectListLgAdapter = ProjectListLgAdapter()

    /**
     * Skill List
     */
    val skillListVisibility: Int = if (shortlistCandidate.getSkillList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    /**
     * WorkExperienceList
     */
    val workExperienceListVisibility: Int = if (shortlistCandidate.getExperienceList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val workExperienceListAdapter: WorkExperienceListAdapter = WorkExperienceListAdapter()

    // ----------------------------------------------------------- //

    init {
        brandListAdapter.updateBrandList(shortlistCandidate.getBrandList())
        brandListLgAdapter.updateBrandList(shortlistCandidate.getBrandList())

        projectListAdapter.updateProjectList(shortlistCandidate.getProjectList())
        projectListLgAdapter.updateProjectList(shortlistCandidate.getProjectList())

        workExperienceListAdapter.updateWorkExperienceList(shortlistCandidate.getExperienceList())
    }

    fun getShortlistCandidate(): ShortlistCandidateEntity {
        return shortlistCandidate
    }
}