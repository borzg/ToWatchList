package com.borzg.towatchlist.ui.watchlist

import androidx.lifecycle.*
import com.borzg.domain.service.WatchListService
import com.borzg.domain.model.CinemaElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchListService: WatchListService,
) : ViewModel() {

    private companion object {
        const val MINUTE = 60 * 1000L
        const val HOUR = MINUTE * 60
        const val ONE_DAY = HOUR * 24
        const val ONE_WEEK = ONE_DAY * 7
        const val ONE_MONTH = ONE_WEEK * 4
    }

    suspend fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        withContext(Dispatchers.IO) {
            watchListService.setWatchedState(isWatched, cinemaElement)
        }
    }

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
