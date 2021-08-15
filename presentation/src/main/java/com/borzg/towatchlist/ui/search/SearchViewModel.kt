package com.borzg.towatchlist.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.borzg.domain.service.SearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchService: SearchService,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun setNewQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchResults = searchQuery.debounce(800).flatMapLatest { query ->
        if (query.isBlank()) flowOf(PagingData.empty())
        else searchService.getMultiSearchResult(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, initialValue = PagingData.empty()).cachedIn(viewModelScope)
}