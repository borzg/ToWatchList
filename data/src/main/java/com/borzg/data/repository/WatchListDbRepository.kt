package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.tv.Tv
import com.borzg.domain.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class WatchListDbRepository @Inject constructor(val cinemaDao: CinemaDao): WatchListRepository {

    override suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        cinemaElement.isWatched = isWatched
        if (cinemaElement is Movie) cinemaDao.updateMovie(cinemaElement)
        if (cinemaElement is Tv) cinemaDao.updateTv(cinemaElement)
    }

    override fun getCinemaElementsFromWatchList(isDisplayed: Boolean): Flow<List<CinemaElement>> {
        val isDisplayedBool = if (isDisplayed) 1 else 0
        return cinemaDao.getMoviesFromWatchList(isDisplayedBool).combine(cinemaDao.getTvsFromWatchList(isDisplayedBool)) { movies, tvs ->
            val combinedList = mutableListOf<CinemaElement>()
            combinedList.addAll(movies)
            combinedList.addAll(tvs)
            combinedList.sortedBy {
                it.addTime
            }
            combinedList
        }
    }
}