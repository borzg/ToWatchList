package com.borzg.data.database

import androidx.room.*
import com.borzg.data.database.model.MovieEntity
import com.borzg.data.database.model.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movie WHERE isDisplayedInWatchList = :isDisplayedInWatchList")
    fun getMoviesFromWatchList(isDisplayedInWatchList: Int = 1): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tv WHERE isDisplayedInWatchList = :isDisplayedInWatchList")
    fun getTvsFromWatchList(isDisplayedInWatchList: Int = 1): Flow<List<TvEntity>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tv WHERE id = :tvId")
    fun getTvById(tvId: Int): Flow<TvEntity>

    @Query("SELECT COUNT(*) FROM movie WHERE isWatched = 1 AND watchedAt > :time")
    fun getNumberOfMovieViewsSinceTime(time: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tv WHERE isWatched = 1 AND watchedAt > :time")
    fun getNumberOfTvViewsSinceTime(time: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: TvEntity)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Update
    suspend fun updateTv(tv: TvEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteTv(tv: TvEntity)
}