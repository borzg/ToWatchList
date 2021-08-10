package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.Movie
import com.borzg.domain.model.tv.Tv
import com.borzg.domain.repository.DetailCinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailServerRepository @Inject constructor(
    private val tmdbApi: TmdbApi
) : DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie> = flow {
        emit(tmdbApi.getMovie(movieId).toDomain())
    }

    override fun getTv(tvId: Int): Flow<Tv> = flow {
        emit(tmdbApi.getTv(tvId).toDomain())
    }

    override suspend fun addMovieToWatchList(movie: Movie) {
        // Do nothing
    }

    override suspend fun updateMovie(movie: Movie) {
        // Do nothing
    }

    override suspend fun addTvToWatchList(tv: Tv) {
        // Do nothing
    }

    override suspend fun updateTv(tv: Tv) {
        // Do nothing
    }

}