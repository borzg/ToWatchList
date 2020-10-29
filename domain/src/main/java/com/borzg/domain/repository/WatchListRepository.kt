package com.borzg.domain.repository

import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

    fun getCinemaElementsFromWatchList(isViewed: Boolean = true): Flow<List<CinemaElement>>
}