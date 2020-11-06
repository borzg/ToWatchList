package com.borzg.towatchlist.ui.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.borzg.data.service.WatchListService
import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.*

class WatchlistViewModel @ViewModelInject constructor(private val watchListService: WatchListService) :
    ViewModel() {

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        withContext(Dispatchers.IO) {
            watchListService.setWatchedState(isWatched, cinemaElement)
        }
    }

    fun getContentFromWatchList(): LiveData<List<CinemaElement>> =
        watchListService.getWatchListContent().asLiveData()
}