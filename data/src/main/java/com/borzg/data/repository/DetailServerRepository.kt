package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.Movie
import com.borzg.domain.repository.DetailCinemaRepository
import io.reactivex.Single
import javax.inject.Inject

class DetailServerRepository @Inject constructor(val tmdbApi: TmdbApi) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Single<Movie> =
        tmdbApi.getMovie(movieId)

    override fun insertMovie(movie: Movie) {
        // Do nothing
    }

}