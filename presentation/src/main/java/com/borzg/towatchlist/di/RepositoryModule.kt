package com.borzg.towatchlist.di

import com.borzg.data.repository.*
import com.borzg.domain.DB
import com.borzg.domain.Server
import com.borzg.domain.repository.AdditionalCinemaRepository
import com.borzg.domain.repository.CinemaSearchRepository
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.repository.WatchListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds @Server @Singleton
    fun bindCinemaDetailServerRepository(cinemaDetailServerRepository: DetailServerRepository) : DetailCinemaRepository

    @Binds @Server @Singleton
    fun bindCinemaSearchServerRepository(cinemaSearchServerRepository: CinemaSearchServerRepository) : CinemaSearchRepository

    @Binds @DB @Singleton
    fun bindCinemaDetailDbRepository(cinemaDetailDbRepository: DetailDbRepository) : DetailCinemaRepository

    @Binds @DB @Singleton
    fun bindWatchListRepository(watchListDbRepository: WatchListDbRepository) : WatchListRepository

    @Binds @Singleton
    fun bindAdditionalCinemaRepository(additionalRepository: AdditionalRepository) : AdditionalCinemaRepository

}