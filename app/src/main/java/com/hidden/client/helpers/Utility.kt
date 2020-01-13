package com.hidden.client.helpers

import android.content.Context
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
}