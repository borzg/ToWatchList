package com.borzg.data.service

import androidx.paging.PagingData
import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchService {

    fun getMultiSearchResult(query: String): Flow<PagingData<SearchResult>>
}