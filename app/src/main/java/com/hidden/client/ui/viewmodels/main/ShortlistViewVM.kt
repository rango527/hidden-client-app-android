package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.Utility
import com.hidden.client.models.ShortlistCandidateEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class ShortlistViewVM(private val shortlistCandidate: ShortlistCandidateEntity) : RootVM() {

    fun getShortlistCandidate(): ShortlistCandidateEntity {
        return shortlistCandidate
    }

    fun getJobTitle(): String {
        return Utility.makeJobString(
            shortlistCandidate.jobTitle_1,
            shortlistCandidate.jobTitle_2,
            shortlistCandidate.jobTitle_3
        )
    }

    fun getJobTitleTextColor(context: Context): Int {
        return Utility.getResourceByName(
            context,
            Enums.Resource.COLOR.value,
            shortlistCandidate.avatarColor
        )
    }

    fun getSnapshotVisibility(): Int {
        return if (shortlistCandidate.hiddenSays == "")
            View.GONE
        else
            View.VISIBLE
    }

}