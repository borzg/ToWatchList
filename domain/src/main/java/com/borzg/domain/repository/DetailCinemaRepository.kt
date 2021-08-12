package com.borzg.domain.repository

import com.borzg.domain.model.Movie
import com.borzg.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface DetailCinemaRepository {

    fun getMovie(movieId: Int): Flow<Movie>

    fun getTv(tvId: Int): Flow<Tv>

    suspend fun addMovieToWatchList(movie: Movie)

    suspend fun updateMovie(movie: Movie)

    suspend fun addTvToWatchList(tv: Tv)

    suspend fun updateTv(tv: Tv)

}