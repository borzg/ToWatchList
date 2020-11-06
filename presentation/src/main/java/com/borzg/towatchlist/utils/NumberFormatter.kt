package com.borzg.towatchlist.utils

import java.lang.StringBuilder
import java.util.*

fun Int.formatToUsDollars() : String =
    numberByDigits(this) + "\$"

fun Long.getDate() : String {
    val date = Date(this)
    return date.toString()
}


fun numberByDigits(num : Int) : String {
    val tmp = StringBuilder(num.toString())
    tmp.reverse()
    val builder = StringBuilder()
    var i = 1
    for (char in tmp) {
        builder.append(char)
        if (i % 3 == 0 && i != tmp.length
            && !(num < 0 && i == tmp.length - 1)) builder.append(" ")
        i++
    }
    return builder.reverse().toString()
}