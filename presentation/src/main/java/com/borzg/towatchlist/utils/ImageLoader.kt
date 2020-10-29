package com.borzg.towatchlist.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.borzg.towatchlist.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File


fun loadImageToCache(imageUrl: String?, context: Context) {
    if (!imageUrl.isNullOrBlank()) {
        val builder = Glide.with(context).downloadOnly().load(BuildConfig.TMDB_IMAGE_URL + imageUrl).into(
            object : SimpleTarget<File>() {
                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                    Log.d("TAG", "onResourceReady: ready!")
                }
            })
    }
}

fun loadImageFromUrl(imageUrl: String?, imageView: ImageView) /*: ByteArray*/ {
    // TODO добавить заглушку
    if (!imageUrl.isNullOrBlank()) Glide.with(imageView.context).load(BuildConfig.TMDB_IMAGE_URL + imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)

//    return Glide.with(imageView.context).`as`(ByteArray::class.java).load(BuildConfig.TMDB_IMAGE_URL + imageUrl).submit().get()
}

fun loadImageFromUrlWithBlur(imageUrl: String?, imageView: ImageView) {
    // TODO добавить заглушку
    if (!imageUrl.isNullOrBlank()) Glide.with(imageView.context).load(BuildConfig.TMDB_IMAGE_URL + imageUrl)
        .apply(bitmapTransform(BlurTransformation(25, 1)))
        .into(imageView)
}