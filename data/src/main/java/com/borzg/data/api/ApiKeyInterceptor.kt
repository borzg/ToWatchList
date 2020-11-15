package com.borzg.data.api

import android.util.Log
import com.borzg.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*


class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()
        val httpUrl: HttpUrl = request.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .addQueryParameter("language", Locale.getDefault().language)
            .build()
        request = request.newBuilder().url(httpUrl).build()

        return chain.proceed(request)
    }

}