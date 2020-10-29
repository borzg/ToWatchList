package com.borzg.domain.repository

import com.borzg.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailCinemaRepository {

    fun getMovie(movieId: Int): Flow<Movie?>

    suspend fun insertMovie(movie: Movie)

    suspend fun updateMovie(movie: Movie)

}