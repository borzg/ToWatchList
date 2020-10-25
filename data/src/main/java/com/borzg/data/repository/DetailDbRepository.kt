package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.Movie
import com.borzg.domain.repository.DetailCinemaRepository
import io.reactivex.Single
import javax.inject.Inject

class DetailDbRepository @Inject constructor(val cinemaDao: CinemaDao) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Single<Movie> =
        cinemaDao.getMovie(movieId)

    override fun insertMovie(movie: Movie) {
        cinemaDao.insertMovie(movie)
    }

}