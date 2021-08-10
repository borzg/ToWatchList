package com.borzg.towatchlist.di

import com.borzg.domain.service.DetailMovieService
import com.borzg.domain.service.DetailTvService
import com.borzg.domain.service.SearchService
import com.borzg.domain.service.WatchListService
import com.borzg.domain.service.implementations.DetailMovieServiceImpl
import com.borzg.domain.service.implementations.DetailTvServiceImpl
import com.borzg.domain.service.implementations.SearchServiceImpl
import com.borzg.domain.service.implementations.WatchListServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ServiceModule {

    @Binds
    fun bindCinemaMovieService(cinemaMovieServiceImpl: DetailMovieServiceImpl): DetailMovieService

    @Binds
    fun bindCinemaTvService(cinemaTvServiceImpl: DetailTvServiceImpl): DetailTvService

    @Binds
    fun bindCinemaSearchService(searchServiceImpl: SearchServiceImpl): SearchService

    @Binds
    fun bindWatchListService(watchListServiceImpl: WatchListServiceImpl) : WatchListService
}