package com.borzg.towatchlist.ui.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.borzg.data.service.SearchService
import com.borzg.data.service.WatchListService

class WatchlistViewModel @ViewModelInject constructor(val watchListService: WatchListService) : ViewModel() {


}