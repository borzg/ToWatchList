package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.Movie
import com.borzg.domain.repository.DetailCinemaRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailDbRepository @Inject constructor(val cinemaDao: CinemaDao) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie?> =
        cinemaDao.getMovie(movieId)

    override suspend fun insertMovie(movie: Movie) {
        // TODO изменить время
        if (movie.isDisplayed != null) {
            if (!movie.isDisplayed!!) {
                movie.addTime = System.currentTimeMillis()
                movie.isDisplayed = true
            }
            cinemaDao.updateMovie(movie)
        } else {
            movie.addTime = System.currentTimeMillis()
            movie.isDisplayed = true
            cinemaDao.insertMovie(movie)
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        cinemaDao.updateMovie(movie)
    }

}