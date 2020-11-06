package com.borzg.towatchlist.ui.watchlist

import com.borzg.domain.model.tv.Tv

class TvWatchListItemViewModel(private val tv: Tv) {

    val title: String
        get() = tv.name

    val firstAirDate: String
        get() = tv.firstAirDate

    val posterPath: String
        get() = tv.posterPath ?: ""
}