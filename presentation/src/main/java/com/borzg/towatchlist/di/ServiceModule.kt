package com.borzg.towatchlist.di

import com.borzg.data.service.DetailElementService
import com.borzg.data.service.SearchService
import com.borzg.data.service.implementations.DetailMovieServiceImpl
import com.borzg.data.service.implementations.SearchServiceImpl
import com.borzg.domain.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface ServiceModule {

    @Binds
    fun bindCinemaMovieService(cinemaMovieServiceImpl: DetailMovieServiceImpl): DetailElementService<Movie>

    @Binds
    fun bindCinemaSearchService(searchServiceImpl: SearchServiceImpl): SearchService
}