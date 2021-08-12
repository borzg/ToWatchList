package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.data.database.model.toEntity
import com.borzg.domain.model.Movie
import com.borzg.domain.model.CinemaElement
import com.borzg.domain.model.Tv
import com.borzg.domain.repository.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WatchListDbRepository @Inject constructor(
    private val cinemaDao: CinemaDao
): WatchListRepository {

    override suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        cinemaElement.isWatched = isWatched
        if (isWatched) cinemaElement.watchedAt = System.currentTimeMillis()
        if (cinemaElement is Movie) cinemaDao.updateMovie(cinemaElement.toEntity())
        if (cinemaElement is Tv) cinemaDao.updateTv(cinemaElement.toEntity())
    }

    override fun getNumberOfViewsSince(sinceTime: Long): Flow<Int> {
        return cinemaDao.getNumberOfMovieViewsSinceTime(sinceTime)
            .combine(cinemaDao.getNumberOfTvViewsSinceTime(sinceTime)) { moviesViews, tvsViews ->
                moviesViews + tvsViews
        }
    }

    override fun getCinemaElementsFromWatchList(isDisplayed: Boolean): Flow<List<CinemaElement>> {
        val isDisplayedBool = if (isDisplayed) 1 else 0
        return cinemaDao.getMoviesFromWatchList(isDisplayedBool).combine(cinemaDao.getTvsFromWatchList(isDisplayedBool)) { movies, tvs ->
            val combinedList = mutableListOf<CinemaElement>()
            combinedList.addAll(movies.map { it.toDomain() })
            combinedList.addAll(tvs.map { it.toDomain() })
            combinedList.sortedByDescending {
                it.addTime
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeItemFromWatchList(cinemaElement: CinemaElement) {
        cinemaElement.isDisplayedInWatchList = false
        if (cinemaElement is Movie) cinemaDao.updateMovie(cinemaElement.toEntity())
        if (cinemaElement is Tv) cinemaDao.updateTv(cinemaElement.toEntity())
    }
}