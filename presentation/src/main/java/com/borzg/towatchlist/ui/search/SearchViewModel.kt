package com.borzg.towatchlist.ui.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.borzg.data.service.SearchService
import com.borzg.domain.model.search.DummySearchResult
import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SearchViewModel @ViewModelInject constructor(private val searchService: SearchService) :
    ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<SearchResult>>? = null

    var isError: Boolean = false

    fun searchData(query: String): Flow<PagingData<SearchResult>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && !isError) {
            return lastResult!!
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<SearchResult>> =
            searchService.getMultiSearchResult(query).cachedIn(viewModelScope)
        isError = false
        currentSearchResult = newResult
        return newResult
    }
}