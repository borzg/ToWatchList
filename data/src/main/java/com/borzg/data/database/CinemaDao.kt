package com.borzg.data.database

import androidx.room.*
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movie, tv WHERE (movie.isViewed = :isViewed OR tv.isViewed = :isViewed)")
    fun getContentOfWatchList(isViewed: Int = 1): Flow<CinemaElement>

    @Query("SELECT * FROM movie WHERE isViewed = :isViewed")
    fun getMoviesFromWatchList(isViewed: Int = 1): Flow<Movie>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    suspend fun isMovieAddedToDb(movieId: Int): Boolean

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

}