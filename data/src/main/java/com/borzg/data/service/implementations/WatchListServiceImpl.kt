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

    override fun getContentOfWatchList(isViewed: Boolean): Flow<CinemaElement> =
        watchListDbRepository.getCinemaElementsFromWatchList(isViewed)
}