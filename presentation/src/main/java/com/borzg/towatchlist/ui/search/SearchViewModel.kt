package com.borzg.towatchlist.ui.search

import dagger.assisted.Assisted
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.borzg.domain.service.SearchService
import com.borzg.domain.model.search.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchService: SearchService,
) : ViewModel() {

    var currentQuery: String = ""

    fun searchData(query: String): Flow<PagingData<SearchResult>> =
        searchService.getMultiSearchResult(query).cachedIn(viewModelScope)

}