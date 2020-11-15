package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.tv.Tv
import com.borzg.domain.repository.DetailCinemaRepository
import kotlinx.coroutines.flow.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class DetailDbRepository @Inject constructor(val cinemaDao: CinemaDao) :
    DetailCinemaRepository {

    override fun getMovie(movieId: Int): Flow<Movie> =
        cinemaDao.getMovieById(movieId).filterNotNull()

    override fun getTv(tvId: Int): Flow<Tv> =
        cinemaDao.getTvById(tvId).filterNotNull()

    override suspend fun addMovieToWatchList(movie: Movie) {
        addCinemaElementToWatchList(movie)
    }

    override suspend fun addTvToWatchList(tv: Tv) {
       addCinemaElementToWatchList(tv)
    }

    override suspend fun updateMovie(movie: Movie) {
        cinemaDao.updateMovie(movie)
    }

    override suspend fun updateTv(tv: Tv) {
        cinemaDao.updateTv(tv)
    }

    private suspend fun addCinemaElementToWatchList(element: CinemaElement) {
        if (element.isDisplayedInWatchList != null) {
            if (!element.isDisplayedInWatchList!!) {
                element.addTime = System.currentTimeMillis()
                element.isDisplayedInWatchList = true
            }
            updateCinemaElement(element)
        } else {
            element.addTime = System.currentTimeMillis()
            element.isDisplayedInWatchList = true
            insertCinemaElement(element)
        }
    }

    private suspend fun insertCinemaElement(cinemaElement: CinemaElement) {
        when(cinemaElement) {
            is Movie -> cinemaDao.insertMovie(cinemaElement)
            is Tv -> cinemaDao.insertTv(cinemaElement)
            else -> throw IllegalArgumentException("Type ${cinemaElement::class} is not yet implemented")
        }
    }

    private suspend fun updateCinemaElement(cinemaElement: CinemaElement) {
        when(cinemaElement) {
            is Movie -> cinemaDao.updateMovie(cinemaElement)
            is Tv -> cinemaDao.updateTv(cinemaElement)
            else -> throw IllegalArgumentException("Type ${cinemaElement::class} is not yet implemented")
        }
    }

}