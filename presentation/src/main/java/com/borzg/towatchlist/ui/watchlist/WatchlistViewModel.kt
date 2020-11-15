package com.borzg.towatchlist.ui.watchlist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.borzg.domain.service.WatchListService
import com.borzg.domain.model.common.CinemaElement
import kotlinx.coroutines.*
import java.util.*

class WatchlistViewModel @ViewModelInject constructor(
    private val watchListService: WatchListService,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        withContext(Dispatchers.IO) {
            watchListService.setWatchedState(isWatched, cinemaElement)
        }
    }

    val MINUTE = 60 * 1000L
    val HOUR = MINUTE * 60
    val ONE_DAY = HOUR * 24
    val ONE_WEEK = ONE_DAY * 7
    val ONE_MONTH = ONE_WEEK * 4

    fun numberOfViewsForWeek(): LiveData<Int> {
        val time = Date().time - ONE_WEEK
        return watchListService.getNumberOfViewsSince(time).asLiveData()
    }

    fun numberOfViewsForMonth(): LiveData<Int> {
        val time = Date().time - ONE_MONTH
        return watchListService.getNumberOfViewsSince(time).asLiveData()
    }

    fun numberOfViewsForAllTime(): LiveData<Int> =
        watchListService.getNumberOfViewsSince(0).asLiveData()

    fun getContentFromWatchList(): LiveData<List<CinemaElement>> =
        watchListService.getWatchListContent().asLiveData()

    fun removeFromWatchList(cinemaElement: CinemaElement) {
        viewModelScope.launch(Dispatchers.IO) {
            watchListService.removeItemFromWatchList(cinemaElement)
        }
    }
}
