package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.AdditionalCinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdditionalRepository @Inject constructor(
    private val tmdbApi: TmdbApi
) : AdditionalCinemaRepository {

    override fun getSimilarMovies(
        movieId: Int,
        pageNumber: Int
    ): Flow<List<MovieSearchResult>> = flow {
        emit(tmdbApi.getSimilarMovies(movieId).results.map { it.toDomain() })
    }
}