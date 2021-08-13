package com.borzg.towatchlist.ui.watchlist

import androidx.lifecycle.*
import com.borzg.domain.service.WatchListService
import com.borzg.domain.model.CinemaElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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

    fun setWatchedState(isWatched: Boolean, cinemaElement: CinemaElement) {
        viewModelScope.launch {
            watchListService.setWatchedState(isWatched, cinemaElement)
        }
    }

    val numberOfViewsForWeek: StateFlow<Int> =
        watchListService.getNumberOfViewsSince(Date().time - ONE_WEEK)
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val numberOfViewsForMonth: StateFlow<Int> =
        watchListService.getNumberOfViewsSince(Date().time - ONE_MONTH)
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val numberOfViewsForAllTime: StateFlow<Int> =
        watchListService.getNumberOfViewsSince(0)
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    fun getContentFromWatchList(): Flow<List<CinemaElement>> =
        watchListService.getWatchListContent()

    fun removeFromWatchList(cinemaElement: CinemaElement) {
        viewModelScope.launch(Dispatchers.IO) {
            watchListService.removeItemFromWatchList(cinemaElement)
        }
    }
}
