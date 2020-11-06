package com.borzg.data.service

import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListService {

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement)

    fun getWatchListContent(isViewed: Boolean = true) : Flow<List<CinemaElement>>
}