package com.borzg.data.service

import com.borzg.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailMovieService {

    fun getMovieDetails(movieId: Int) : Flow<Movie>

    suspend fun addMovieToWatchList(movie: Movie)
}