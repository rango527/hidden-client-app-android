package com.hidden.client.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object HCDate {

    fun stringToDate(dateStr: String, fmtStr: String?): Date? {

        val str = fmtStr ?: "yyyy-MM-dd'T'HH:mm:ssZ"
        val format = SimpleDateFormat(str, Locale.getDefault())
        try {
            return format.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun convertUTCDateStringToLocal(dateStr: String, fmtStr: String?): Date? {

        if (dateStr == "") {
            return null
        }

        val str = fmtStr ?: "yyyy-MM-dd'T'HH:mm:ssZ"
        val format = SimpleDateFormat(str, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        try {
            return format.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun dateToString(date: Date, fmtStr: String?): String? {

        val str = fmtStr ?: "yyyy-MM-dd'T'HH:mm:ssZ"
        val dateFormat = SimpleDateFormat(str, Locale.getDefault())
        try {
            return dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun dayPrefix(day: String): String {
        return when (day) {
            "1" -> {
                "1st";
            }
            "2" -> {
                "2nd"
            }
            "3" -> {
                "3rd"
            }
            else -> {
                """${day}th"""
            }
        }
    }
}
