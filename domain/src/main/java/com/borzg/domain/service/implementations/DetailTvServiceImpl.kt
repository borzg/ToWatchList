package com.borzg.domain.service.implementations

import com.borzg.domain.DB
import com.borzg.domain.Server
import com.borzg.domain.model.Tv
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.service.DetailTvService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailTvServiceImpl @Inject constructor(
    @param:DB private val dbRepository: DetailCinemaRepository,
    @param:Server private val serverRepository: DetailCinemaRepository
): DetailTvService {

    var tvStateKeeper: Tv? = null
        @Synchronized get
        @Synchronized set

    @FlowPreview
    override fun getTvDetails(tvId: Int): Flow<Tv> = flowOf(
        dbRepository.getTv(tvId),
        serverRepository.getTv(tvId)
            .catch {
                it.printStackTrace()
            }
    ).flattenMerge().onEach { tv ->
        // On first iteration or if element comes from DB
        if (tvStateKeeper == null || tv.addTime != null) {
            tvStateKeeper = tv
        } else {
            // When item element from server
            tv.copyCinemaElementParametersFrom(tvStateKeeper!!)
            tvStateKeeper = tv
            dbRepository.updateTv(tv)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addTvToWatchList(tv: Tv) {
        dbRepository.addTvToWatchList(tv)
    }
}