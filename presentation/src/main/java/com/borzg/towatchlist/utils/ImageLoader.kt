package com.borzg.towatchlist.utils

import android.widget.ImageView
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.bumptech.glide.Glide


fun loadImageFromUrl(imageUrl: String?, imageView: ImageView) {
    // TODO добавить заглушку
    if (!imageUrl.isNullOrBlank()) Glide.with(imageView.context).load(BuildConfig.TMDB_IMAGE_URL + imageUrl)
        .into(imageView)
}