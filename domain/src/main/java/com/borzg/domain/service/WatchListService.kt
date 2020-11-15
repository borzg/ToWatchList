package com.borzg.domain.service

import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListService {

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement)

    fun getNumberOfViewsSince(sinceTime: Long): Flow<Int>

    fun getWatchListContent(isViewed: Boolean = true): Flow<List<CinemaElement>>

    suspend fun removeItemFromWatchList(cinemaElement: CinemaElement)
}