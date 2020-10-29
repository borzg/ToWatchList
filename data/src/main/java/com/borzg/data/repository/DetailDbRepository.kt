package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.Movie
import com.borzg.domain.repository.DetailCinemaRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailDbRepository @Inject constructor(val cinemaDao: CinemaDao) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie?> =
        cinemaDao.getMovie(movieId)

    override suspend fun insertMovie(movie: Movie) {
        // TODO изменить время
        if (movie.isViewed != null) {
            if (!movie.isViewed!!) {
                movie.addTime = System.currentTimeMillis()
                movie.isViewed = true
            }
            cinemaDao.updateMovie(movie)
        } else {
            movie.addTime = System.currentTimeMillis()
            movie.isViewed = true
            cinemaDao.insertMovie(movie)
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        cinemaDao.updateMovie(movie)
    }

}