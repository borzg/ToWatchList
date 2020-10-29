package com.borzg.towatchlist.di

import android.content.Context
import androidx.room.Room
import com.borzg.data.database.CinemaDao
import com.borzg.data.database.CinemaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    fun provideCinemaDatabase(@ApplicationContext context: Context): CinemaDatabase =
        Room.databaseBuilder(context, CinemaDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCinemaDao(cinemaDatabase: CinemaDatabase): CinemaDao =
        cinemaDatabase.cinemaDao()
}