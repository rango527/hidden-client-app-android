package com.hidden.client.helpers

import android.content.Context
import com.hidden.client.R
import com.hidden.client.helpers.extension.safeValue

object Utility {

    fun makeJobString(job1: String?, job2: String?, job3: String?): String {

        val jobArray: Array<String?> = arrayOf(job1, job2, job3)
        var jobStr = ""

        for (job in jobArray) {
            if (job.safeValue() != "") {
                if (jobStr != "") {
                    jobStr += " | "
                }
                jobStr += job
            }
        }

        return jobStr
    }

    fun getResourceByName(context: Context, defType: String, name: String): Int {
        return context.resources.getIdentifier(name, defType, context.packageName)
    }

    fun getReviewTextFromType(reviewType: Int): String {
        return when (reviewType) {
            Enums.ReviewerType.SHORTLIST_REVIEWER.value -> "submission_reviewer"
            Enums.ReviewerType.INTERVIEWER.value -> "interviewer"
            Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> "interview_advancer"
            Enums.ReviewerType.OFFER_MANAGER.value -> "offer_manager"
            else -> ""
        }
    }

    fun getBackgroundResourceFromCurrentStatus(color: String): Int =
        when (color) {
            Enums.ColorType.BLUE.value -> R.drawable.progress_item_current
            Enums.ColorType.GREEN.value -> R.drawable.progress_item_complete
            Enums.ColorType.RED.value -> R.drawable.progress_item_red
            else -> R.drawable.progress_item_incomplete
        }

    fun getTriangleBackgroundResourceByStatus(color: String): Int =
        when (color) {
            Enums.ColorType.BLUE.value -> R.drawable.panel_triangle_blue
            Enums.ColorType.GREEN.value -> R.drawable.panel_triangle_green
            Enums.ColorType.GREY.value -> R.drawable.panel_triangle_gray
            Enums.ColorType.RED.value -> R.drawable.panel_triangle_red
            else -> R.drawable.panel_triangle_transparent
        }

    fun getTileBackgroundResourceByStatus(color: String): Int =
        when (color) {
            Enums.ColorType.BLUE.value -> R.drawable.progress_item_current_12
            Enums.ColorType.GREEN.value -> R.drawable.progress_item_complete_12
            Enums.ColorType.RED.value -> R.drawable.progress_item_red_12
            else -> R.drawable.progress_item_incomplete_12
        }

    fun getStageClientTileIcon(icon: String): Int =
        when (icon) {
            "turtle" -> R.drawable.tile_turtle
            "horse-saddle" -> R.drawable.tile_horse
            "loveseat" ->R.drawable.tile_chair
            "check" -> R.drawable.tile_tick
            "forward" -> R.drawable.tile_right_arrow

            "user-plus" -> R.drawable.tick_smile
            "tachometer-slowest" -> R.drawable.tick_smile
            "traffic-light-stop" -> R.drawable.tick_smile
            else -> R.drawable.tick_smile
        }
}