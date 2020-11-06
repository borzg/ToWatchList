package com.borzg.data.database

import androidx.room.*
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.tv.Tv
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movie WHERE isDisplayed = :isDisplayed")
    fun getMoviesFromWatchList(isDisplayed: Int = 1): Flow<List<Movie>>

    @Query("SELECT * FROM tv WHERE isDisplayed = :isDisplayed")
    fun getTvsFromWatchList(isDisplayed: Int = 1): Flow<List<Tv>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Update
    suspend fun updateTv(tv: Tv)

}