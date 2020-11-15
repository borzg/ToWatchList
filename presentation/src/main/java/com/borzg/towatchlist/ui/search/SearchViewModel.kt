package com.borzg.towatchlist.ui.search

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.borzg.domain.service.SearchService
import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.flow.*

class SearchViewModel @ViewModelInject constructor(
    private val searchService: SearchService,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentQuery: String = ""

    fun searchData(query: String): Flow<PagingData<SearchResult>> =
        searchService.getMultiSearchResult(query).cachedIn(viewModelScope)

}