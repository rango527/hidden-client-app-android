package com.hidden.client.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.hidden.client.datamodels.HCShortlistCandidateResponse
import com.hidden.client.models.custom.*
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel

class HCGlobal{

    init { }

    companion object {

        private var INSTANCE: HCGlobal = HCGlobal()

        fun getInstance() : HCGlobal{
            return INSTANCE
        }

        const val LOG_TAG: String = "HiddenClient"
    }

    fun log(message: String) {
        if (APP.is_debug) {
            Log.d(LOG_TAG, message)
        }
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun convertPxToDp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    // Global variable
    lateinit var currentActivity: Activity           // current activity instance

    lateinit var currentJobTitleList: ArrayList<HCJobDetailTileViewModel>

    var currentShortlist: List<HCShortlistCandidateResponse> = arrayListOf()

    var isAdmin: Boolean = false
    var currentClientUrl: String = ""
    var currentCompanyLogoUrl: String = ""

    var getAllJobList: ArrayList<GetAllJob> = arrayListOf()
    var ShortlistJobList: ArrayList<ShortlistJob> = arrayListOf()

    var getJobPick: ArrayList<JobPick> = arrayListOf()          //job temp

    var imageMessageList: ArrayList<ImageMessageList> = arrayListOf()

    var currentProcessFilterList = ProcessFilterList()
    var tempProcessFilterList = ProcessFilterList()             //process temp

    var currentIndex: Int = 0
    var currentMessageCount: Int = 0
    var currentProjectIndex: Int = 0
    var currentJobId: Int = 0
    var currentFeedbackId: Int = 0
    var currentAvatarName: String = ""

    var conversationId: Int = 0

    // Read Status Global
    var currentReadStatus: Int = -1             //-1: all messages, 0: has no unread messages, 1: has unread messages

    var shortlistCandidateVMList: List<ShortlistViewVM> = listOf()
}