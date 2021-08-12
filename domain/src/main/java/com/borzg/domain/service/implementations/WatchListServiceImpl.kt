package com.borzg.domain.service.implementations

import com.borzg.domain.DB
import com.borzg.domain.model.CinemaElement
import com.borzg.domain.repository.WatchListRepository
import com.borzg.domain.service.WatchListService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListServiceImpl @Inject constructor(
    @param:DB private val watchListDbRepository: WatchListRepository
) : WatchListService {

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