package com.borzg.data.service.implementations

import com.borzg.data.service.WatchListService
import com.borzg.domain.model.DB
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListServiceImpl @Inject constructor() : WatchListService {

    @Inject @DB
    lateinit var watchListDbRepository: WatchListRepository

    override suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        watchListDbRepository.setWatchedState(isWatched, cinemaElement)
    }

    override fun getWatchListContent(isDisplayed: Boolean): Flow<List<CinemaElement>> =
        watchListDbRepository.getCinemaElementsFromWatchList(isDisplayed)
}