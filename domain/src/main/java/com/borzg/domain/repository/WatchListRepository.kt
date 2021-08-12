package com.borzg.domain.repository

import com.borzg.domain.model.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement)

    fun getNumberOfViewsSince(sinceTime: Long = 0): Flow<Int>

    fun getCinemaElementsFromWatchList(isDisplayed: Boolean = true): Flow<List<CinemaElement>>

    suspend fun removeItemFromWatchList(cinemaElement: CinemaElement)
}