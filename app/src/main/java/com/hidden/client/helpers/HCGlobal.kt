package com.hidden.client.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.hidden.client.datamodels.HCShortlistCandidateResponse
import com.hidden.client.models_.HCLogin
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

    lateinit var currrentJobTitleList: ArrayList<HCJobDetailTileViewModel>

    var currentShortlist: List<HCShortlistCandidateResponse> = arrayListOf()
    var currentIndex: Int = 0
    var currentProjectIndex: Int = 0

    var shortlistCandidateVMList: List<ShortlistViewVM> = listOf()
}