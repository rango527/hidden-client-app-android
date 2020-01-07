package com.hidden.client.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HCDate {

    fun stringToDate(dateStr: String, fmtStr: String?): Date? {

        val str = fmtStr ?: "yyyy-MM-dd'T'HH:mm:ssZ"
        val format = SimpleDateFormat(str, Locale.US)
        try {
            return format.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun dateToString(date: Date, fmtStr: String?): String? {

        val str = fmtStr ?: "yyyy-MM-dd'T'HH:mm:ssZ"
        val dateFormat = SimpleDateFormat(str, Locale.US)
        try {
            return dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
