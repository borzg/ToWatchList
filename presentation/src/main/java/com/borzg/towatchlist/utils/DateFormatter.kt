package com.borzg.towatchlist.utils

import android.content.Context
import com.borzg.towatchlist.R
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * Receives a date in format yyyy-dd-MM and returns year
 */
fun getYearFromStringDate(date: String?): String {
    if (date.isNullOrBlank()) return ""
    return try {
        val formattedDate = SimpleDateFormat("yyyy-dd-MM").parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = formattedDate
        calendar.get(Calendar.YEAR).toString()
    } catch (exception: Exception) {
        ""
    }
}

const val MINUTES_HOUR = 60
const val MINUTES_DAY = 24 * MINUTES_HOUR

fun Int.minutesToTimeFormat(): String {
    val builder = StringBuilder()
    val days = this / MINUTES_DAY
    val hours = (this - days * MINUTES_DAY) / MINUTES_HOUR
    val minutes = (this - hours * MINUTES_HOUR - days * MINUTES_DAY)
    builder.apply {
        if (days != 0) append(days).append(":")
        if (hours != 0) {
            when {
                hours > 9 -> append(hours)
                days != 0 -> append(0).append(hours)
                else -> append(hours)
            }
            append(":")
        }
        if (minutes > 9) append(minutes)
        else if (days != 0 || hours != 0) append(0).append(minutes)
        else append(minutes)
    }
    if (this < MINUTES_HOUR) builder.append(":00")
    return builder.toString()
}

const val MILLISECOND_SECOND = 1000L
const val MILLISECOND_MINUTE = MILLISECOND_SECOND * 60
const val MILLISECOND_HOUR = MILLISECOND_MINUTE * 60
const val MILLISECOND_DAY = MILLISECOND_HOUR * 24
//const val MILLISECOND_WEEK = MILLISECOND_DAY * 7
//const val MILLISECOND_MONTH_AVERAGE = MILLISECOND_DAY * 30
//const val MILLISECOND_YEAR = MILLISECOND_DAY * 365

fun Long?.millisecondsToTimePeriod(context: Context): String {
    if (this == null) return ""
    val _this = Calendar.getInstance()
    _this.time = Date(this)
    val current = Calendar.getInstance()
    val currentTime = current.timeInMillis
    val difference = currentTime - this

    if (difference < 0) return "Unknown"

    if (difference < MILLISECOND_DAY && _this.get(Calendar.DAY_OF_WEEK) == current.get(Calendar.DAY_OF_WEEK))
        return context.resources.getString(R.string.today)
    else if (_this.get(Calendar.DAY_OF_WEEK) != current.get(Calendar.DAY_OF_WEEK)) return context.resources.getString(R.string.yesterday)

    return "${_this.get(Calendar.DAY_OF_MONTH)} ${context.resources.getStringArray(R.array.at_month)[_this.get(Calendar.MONTH)]} ${_this.get(Calendar.YEAR)}"
}

fun formatYearsPeriod(startDate: String?, endDate: String?, isEndless: Boolean = false): String {
    val start = getYearFromStringDate(startDate)
    return if (!isEndless) {
        val end = getYearFromStringDate(endDate)
        if (start == end) start
        else "$start-$end"
    }
    else "$start-..."
}

fun String?.getYearFromDate() =
    getYearFromStringDate(this)

