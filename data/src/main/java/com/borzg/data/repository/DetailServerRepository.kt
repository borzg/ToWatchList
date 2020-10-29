package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.Movie
import com.borzg.domain.repository.DetailCinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailServerRepository @Inject constructor(val tmdbApi: TmdbApi) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie?> = flow {
        emit(tmdbApi.getMovie(movieId))
    }

    override suspend fun insertMovie(movie: Movie) {
        // Do nothing
    }

    override suspend fun updateMovie(movie: Movie) {
        // Do nothing
    }

}