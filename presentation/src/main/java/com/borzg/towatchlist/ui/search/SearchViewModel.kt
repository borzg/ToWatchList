package com.borzg.towatchlist.ui.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.borzg.data.service.SearchService
import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class SearchViewModel @ViewModelInject constructor(private val searchService: SearchService) : ViewModel() {

    private var _isDataTheSame = MutableLiveData<Boolean>(false)
    val isDataTheSame: LiveData<Boolean>
        get() = _isDataTheSame

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<SearchResult>>? = null

    fun searchData(query: String): Flow<PagingData<SearchResult>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            _isDataTheSame.postValue(true)
            return lastResult
        }
        _isDataTheSame.postValue(false)
        currentQueryValue = query
        val newResult: Flow<PagingData<SearchResult>> = searchService.getMultiSearchResult(query)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}