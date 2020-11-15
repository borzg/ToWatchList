package com.borzg.towatchlist.di

import com.borzg.data.api.ApiKeyInterceptor
import com.borzg.data.api.MultiTypeRequestDeserializer
import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideGson() : Gson {
        return GsonBuilder()
            .registerTypeAdapter(SearchResult::class.java, MultiTypeRequestDeserializer())
            .create()
    }

    @Provides @Singleton
    fun provideClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(gson : Gson, client : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideTmbdApi(retrofit: Retrofit) : TmdbApi {
        return retrofit.create(TmdbApi::class.java)
    }
}