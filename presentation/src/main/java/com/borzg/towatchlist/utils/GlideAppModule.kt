package com.borzg.towatchlist.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSize = 1024 * 1024 * 20
        builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))
            .setDiskCache(InternalCacheDiskCacheFactory(context, memoryCacheSize.toLong()))
            .setDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
    }

}