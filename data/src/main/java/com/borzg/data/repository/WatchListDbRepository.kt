package com.borzg.data.repository

import android.util.Log
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
        val newElement = cinemaElement.copyLocalCinemaElementParameters(
            isWatched = isWatched,
            watchedAt = if (isWatched) System.currentTimeMillis() else cinemaElement.watchedAt
        )
        when(newElement) {
            is Movie -> cinemaDao.updateMovie(newElement.toEntity())
            is Tv -> cinemaDao.updateTv(newElement.toEntity())
        }
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
            Log.d("TAG", "getCinemaElementsFromWatchList: $movies")
            combinedList.sortedByDescending {
                it.addTime
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeItemFromWatchList(cinemaElement: CinemaElement) {
        val newElement = cinemaElement.copyLocalCinemaElementParameters(
            isDisplayedInWatchList = false
        )
        when(newElement) {
            is Movie -> cinemaDao.updateMovie(newElement.toEntity())
            is Tv -> cinemaDao.updateTv(newElement.toEntity())
        }
    }
}