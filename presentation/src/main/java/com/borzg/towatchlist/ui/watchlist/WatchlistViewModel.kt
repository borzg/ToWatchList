package com.borzg.towatchlist.ui.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.borzg.data.service.WatchListService
import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.flow.Flow

class WatchlistViewModel @ViewModelInject constructor(val watchListService: WatchListService) : ViewModel() {


    fun getContentFromWatchList() : LiveData<List<CinemaElement>> =
        watchListService.getWatchListContent().asLiveData()
}