package com.borzg.towatchlist.di

import com.borzg.data.service.DetailMovieService
import com.borzg.data.service.SearchService
import com.borzg.data.service.WatchListService
import com.borzg.data.service.implementations.DetailMovieServiceImpl
import com.borzg.data.service.implementations.SearchServiceImpl
import com.borzg.data.service.implementations.WatchListServiceImpl
import com.borzg.domain.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface ServiceModule {

    @Binds
    fun bindCinemaMovieService(cinemaMovieServiceImpl: DetailMovieServiceImpl): DetailMovieService

    @Binds
    fun bindCinemaSearchService(searchServiceImpl: SearchServiceImpl): SearchService

    @Binds
    fun bindWatchListService(watchListServiceImpl: WatchListServiceImpl) : WatchListService
}