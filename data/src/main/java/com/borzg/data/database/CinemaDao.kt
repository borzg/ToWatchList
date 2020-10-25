package com.borzg.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borzg.domain.model.Movie
import io.reactivex.Single

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Single<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

}