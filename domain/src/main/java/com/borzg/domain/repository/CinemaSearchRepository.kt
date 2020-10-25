package com.borzg.domain.repository

import com.borzg.domain.model.search.SearchResult

interface CinemaSearchRepository {

    suspend fun getMultiSearchResult(query: String, page: Int) : List<SearchResult>
}