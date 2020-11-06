package com.borzg.domain.repository

import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

   suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement)

    fun getCinemaElementsFromWatchList(isDisplayed: Boolean = true): Flow<List<CinemaElement>>
}