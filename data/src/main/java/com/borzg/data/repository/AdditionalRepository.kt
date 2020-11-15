package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.AdditionalCinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdditionalRepository @Inject constructor(val tmdbApi: TmdbApi) : AdditionalCinemaRepository {

    override fun getSimilarMovies(movieId: Int, pageNumber: Int): Flow<List<SearchResult.MovieSearchResult>> = flow {
        emit(tmdbApi.getSimilarMovies(movieId).results)
    }
}