package com.borzg.towatchlist.utils

fun Int.formatToUsDollars() : String =
    numberByDigits(this.toLong()) + "\$"

fun Long.formatToUsDollars() : String =
    numberByDigits(this) + "\$"

fun numberByDigits(num : Long) : String {
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