package com.borzg.towatchlist.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.ArraySet
import android.util.Log
import android.util.MutableInt
import android.util.SparseArray
import android.widget.ImageView
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
        .error(R.mipmap.cinema_dummy)
        .fallback(R.mipmap.cinema_dummy)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun loadImageFromUrlWithBlur(imageUrl: String?, imageView: ImageView) {
    GlideApp.with(imageView.context).load(requestFromImageUrl(imageUrl, BIG_SIZE))
        .apply(bitmapTransform(BlurTransformation(25, 1)))
        .error(R.mipmap.cinema_dummy)
        .fallback(R.mipmap.cinema_dummy)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun requestFromImageUrl(imageUrl: String?, size: Int) : String? {
    if (imageUrl.isNullOrBlank()) return null
    val builder = StringBuilder(BuildConfig.TMDB_IMAGE_URL)
    builder.append("w").append(size).append(imageUrl)
    return builder.toString()
}