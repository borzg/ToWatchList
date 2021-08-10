package com.borzg.towatchlist.di

import com.borzg.data.api.ApiKeyInterceptor
import com.borzg.data.api.MultiTypeRequestDeserializer
import com.borzg.data.api.TmdbApi
import com.borzg.data.api.model.search.SearchResultNetwork
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides @Singleton
    fun provideGson() : Gson {
        return GsonBuilder()
            .registerTypeAdapter(SearchResultNetwork::class.java, MultiTypeRequestDeserializer())
            .create()
    }

    @Provides @Singleton
    fun provideClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(httpLoggingInterceptor)
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