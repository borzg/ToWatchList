package com.borzg.towatchlist.di

import com.borzg.data.repository.CinemaSearchServerRepository
import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.data.repository.DetailServerRepository
import com.borzg.data.repository.DetailDbRepository
import com.borzg.domain.repository.CinemaSearchRepository
import com.borzg.domain.repository.DetailCinemaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds @Server @Singleton
    fun bindCinemaDetailServerRepository(cinemaDetailServerRepository: DetailServerRepository) : DetailCinemaRepository

    @Binds @Server @Singleton
    fun bindCinemaSearchServerRepository(cinemaSearchServerRepository: CinemaSearchServerRepository) : CinemaSearchRepository

    @Binds @DB @Singleton
    fun bindCinemaDetailDbRepository(cinemaDetailDbRepository: DetailDbRepository) : DetailCinemaRepository
}