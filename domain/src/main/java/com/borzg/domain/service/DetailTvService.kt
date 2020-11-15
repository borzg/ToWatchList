package com.borzg.domain.service

import com.borzg.domain.model.Movie
import com.borzg.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow

interface DetailTvService {

    fun getTvDetails(tvId: Int): Flow<Tv>

    suspend fun addTvToWatchList(tv: Tv)
}