package com.borzg.data.repository

import android.util.Log
import com.borzg.data.api.TmdbApi
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.CinemaSearchRepository
import javax.inject.Inject

class CinemaSearchServerRepository @Inject constructor(val tmdbApi: TmdbApi) :
    CinemaSearchRepository {

    override suspend fun getMultiSearchResult(query: String, page: Int): List<SearchResult> =
        tmdbApi.getMultiSearchResult(query, page).results

}