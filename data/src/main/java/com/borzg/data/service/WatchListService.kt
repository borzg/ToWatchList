package com.borzg.data.service

import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

interface WatchListService {

    fun getWatchListContent(isViewed: Boolean = true) : Flow<List<CinemaElement>>
}