package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class WatchListDbRepository @Inject constructor(val cinemaDao: CinemaDao): WatchListRepository {

//    override fun getCinemaElementsFromWatchList(isViewed: Boolean): Flow<List<CinemaElement>> =
//        cinemaDao.getContentOfWatchList(if (isViewed) 1 else 0)

    override fun getCinemaElementsFromWatchList(isViewed: Boolean): Flow<List<CinemaElement>> {
        val isViewedBool = if (isViewed) 1 else 0
        return cinemaDao.getMoviesFromWatchList(isViewedBool).combine(cinemaDao.getTvsFromWatchList(isViewedBool)) { movies, tvs ->
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