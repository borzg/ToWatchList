package com.borzg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borzg.data.database.model.MovieEntity
import com.borzg.data.database.model.PersonEntity
import com.borzg.data.database.model.TvEntity

@Database(entities = [MovieEntity::class, PersonEntity::class, TvEntity::class], version = 1)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun cinemaDao() : CinemaDao
}