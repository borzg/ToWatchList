package com.borzg.data.repository

import com.borzg.data.database.CinemaDao
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListDbRepository @Inject constructor(val cinemaDao: CinemaDao): WatchListRepository {

    override fun getCinemaElementsFromWatchList(isViewed: Boolean): Flow<CinemaElement> =
        cinemaDao.getMoviesFromWatchList(if (isViewed) 1 else 0)
}