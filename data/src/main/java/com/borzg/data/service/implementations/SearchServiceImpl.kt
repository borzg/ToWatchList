package com.borzg.data.service.implementations

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.borzg.data.MultiSearchSource
import com.borzg.data.TMDB_PAGE_SIZE
import com.borzg.data.service.SearchService
import com.borzg.domain.model.Server
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.CinemaSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchServiceImpl @Inject constructor() :
    SearchService {

    @Inject @Server
    lateinit var cinemaSearchRepository: CinemaSearchRepository

    override fun getMultiSearchResult(query: String): Flow<PagingData<SearchResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MultiSearchSource(cinemaSearchRepository, query) }
        ).flow
    }

}