package com.borzg.domain.repository

import com.borzg.domain.model.Movie
import io.reactivex.Single

interface DetailCinemaRepository {

    fun getMovie(movieId: Int): Single<Movie>

    fun insertMovie(movie: Movie)

}