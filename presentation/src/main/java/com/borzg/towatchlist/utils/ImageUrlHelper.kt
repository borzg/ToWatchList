package com.borzg.towatchlist.utils

import com.borzg.towatchlist.BuildConfig

const val SMALL_SIZE = 200
const val MEDIUM_SIZE = 400
const val BIG_SIZE = 500

fun requestFromImageUrl(imageUrl: String?, size: Int) : String? {
    if (imageUrl.isNullOrBlank()) return null
    val builder = StringBuilder(BuildConfig.TMDB_IMAGE_URL)
    builder.append("w").append(size).append(imageUrl)
    return builder.toString()
}