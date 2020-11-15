package com.borzg.towatchlist.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import java.lang.StringBuilder

const val SMALL_SIZE = 200
const val MEDIUM_SIZE = 400
const val BIG_SIZE = 500

@SuppressLint("CheckResult")
fun loadImageToCache(imageUrl: String?, context: Context) {
    if (!imageUrl.isNullOrBlank()) {
        GlideApp.with(context).downloadOnly().load(requestFromImageUrl(imageUrl, BIG_SIZE))
    }
}

fun loadImageFromUrl(imageUrl: String?, imageView: ImageView) {
    GlideApp.with(imageView.context).load(requestFromImageUrl(imageUrl, SMALL_SIZE))
        .error(R.drawable.cinema_dummy)
        .fallback(R.drawable.cinema_dummy)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    loadImageFromUrl(imageUrl, this)
}

fun loadImageFromUrlWithBlur(imageUrl: String?, imageView: ImageView) {
    GlideApp.with(imageView.context).load(requestFromImageUrl(imageUrl, BIG_SIZE))
        .apply(bitmapTransform(BlurTransformation(25, 1)))
        .error(R.drawable.cosmos)
        .fallback(R.drawable.cosmos)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun requestFromImageUrl(imageUrl: String?, size: Int) : String? {
    if (imageUrl.isNullOrBlank()) return null
    val builder = StringBuilder(BuildConfig.TMDB_IMAGE_URL)
    builder.append("w").append(size).append(imageUrl)
    return builder.toString()
}