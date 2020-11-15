package com.borzg.domain.service.implementations

import com.borzg.domain.model.DB
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.repository.WatchListRepository
import com.borzg.domain.service.WatchListService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListServiceImpl @Inject constructor() : WatchListService {

    @Inject @DB
    lateinit var watchListDbRepository: WatchListRepository

    override suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        watchListDbRepository.setWatchedState(isWatched, cinemaElement)
    }

    override fun getNumberOfViewsSince(sinceTime: Long): Flow<Int> =
        watchListDbRepository.getNumberOfViewsSince(sinceTime)

    override fun getWatchListContent(isViewed: Boolean): Flow<List<CinemaElement>> =
        watchListDbRepository.getCinemaElementsFromWatchList(isViewed)

    override suspend fun removeItemFromWatchList(cinemaElement: CinemaElement) {
        watchListDbRepository.removeItemFromWatchList(cinemaElement)
    }
}