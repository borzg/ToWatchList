package com.borzg.towatchlist.di

import com.borzg.data.repository.*
import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.domain.repository.AdditionalCinemaRepository
import com.borzg.domain.repository.CinemaSearchRepository
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.repository.WatchListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
interface RepositoryModule {

    @Binds @Server
    fun bindCinemaDetailServerRepository(cinemaDetailServerRepository: DetailServerRepository) : DetailCinemaRepository

    @Binds @Server
    fun bindCinemaSearchServerRepository(cinemaSearchServerRepository: CinemaSearchServerRepository) : CinemaSearchRepository

    @Binds @DB
    fun bindCinemaDetailDbRepository(cinemaDetailDbRepository: DetailDbRepository) : DetailCinemaRepository

    @Binds @DB
    fun bindWatchListRepository(watchListDbRepository: WatchListDbRepository) : WatchListRepository

    @Binds
    fun bindAdditionalCinemaRepository(additionalRepository: AdditionalRepository) : AdditionalCinemaRepository

}