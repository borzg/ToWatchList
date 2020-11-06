package com.borzg.towatchlist.utils

import java.text.SimpleDateFormat
import java.util.*


fun getYear(date: String?) : String? {
    if (date == null || date == "") return null
    return try {
        val formattedDate = SimpleDateFormat("yyyy-dd-MM").parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = formattedDate
        calendar.get(Calendar.YEAR).toString()
    } catch (exception: Exception) {
        null
    }
}

