package com.borzg.data.repository

import com.borzg.data.api.TmdbApi
import com.borzg.data.api.model.search.toDomainOrNull
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.CinemaSearchRepository
import javax.inject.Inject

class CinemaSearchServerRepository @Inject constructor(
    private val tmdbApi: TmdbApi
) : CinemaSearchRepository {

    override suspend fun getMultiSearchResult(query: String, page: Int): List<SearchResult> =
        tmdbApi.getMultiSearchResult(query, page).results.mapNotNull { it.toDomainOrNull() }

}