package com.borzg.domain.service.implementations

import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.domain.model.tv.Tv
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.service.DetailTvService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailTvServiceImpl @Inject constructor(): DetailTvService {

    @Inject @DB
    lateinit var dbRepository: DetailCinemaRepository

    @Inject @Server
    lateinit var serverRepository: DetailCinemaRepository

    var tvStateKeeper: Tv? = null
        @Synchronized get
        @Synchronized set

    @FlowPreview
    override fun getTvDetails(tvId: Int): Flow<Tv> = flowOf(
        dbRepository.getTv(tvId),
        serverRepository.getTv(tvId)
            .catch { println("getMovieDetails from server: $it ${it.message}") }
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