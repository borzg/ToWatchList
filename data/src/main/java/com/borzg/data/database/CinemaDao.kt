package com.borzg.data.database

import androidx.room.*
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.tv.Tv
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movie WHERE isDisplayedInWatchList = :isDisplayedInWatchList")
    fun getMoviesFromWatchList(isDisplayedInWatchList: Int = 1): Flow<List<Movie>>

    @Query("SELECT * FROM tv WHERE isDisplayedInWatchList = :isDisplayedInWatchList")
    fun getTvsFromWatchList(isDisplayedInWatchList: Int = 1): Flow<List<Tv>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<Movie>

    @Query("SELECT * FROM tv WHERE id = :tvId")
    fun getTvById(tvId: Int): Flow<Tv>

    @Query("SELECT COUNT(*) FROM movie WHERE isWatched = 1 AND watchedAt > :time")
    fun getNumberOfMovieViewsSinceTime(time: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tv WHERE isWatched = 1 AND watchedAt > :time")
    fun getNumberOfTvViewsSinceTime(time: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: Tv)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Update
    suspend fun updateTv(tv: Tv)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Delete
    suspend fun deleteTv(tv: Tv)
}