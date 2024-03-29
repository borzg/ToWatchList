package com.borzg.domain.service

import com.borzg.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface DetailTvService {

    fun getTvDetails(tvId: Int): Flow<Tv>

    suspend fun addTvToWatchList(tv: Tv)
}