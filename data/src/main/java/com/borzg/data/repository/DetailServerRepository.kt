package com.borzg.data.repository

import android.util.Log
import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.Movie
import com.borzg.domain.model.tv.Tv
import com.borzg.domain.repository.DetailCinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class DetailServerRepository @Inject constructor(val tmdbApi: TmdbApi) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie> = flow {
        emit(tmdbApi.getMovie(movieId))
    }

    override fun getTv(tvId: Int): Flow<Tv> = flow {
        emit(tmdbApi.getTv(tvId))
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