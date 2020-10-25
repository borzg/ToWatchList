package com.borzg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borzg.domain.model.Movie
import com.borzg.domain.model.Person
import com.borzg.domain.model.tv.Tv

@Database(entities = [Movie::class, Person::class, Tv::class], version = 1)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun cinemaDao() : CinemaDao
}