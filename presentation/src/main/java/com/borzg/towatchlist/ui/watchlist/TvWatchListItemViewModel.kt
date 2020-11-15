package com.borzg.towatchlist.ui.watchlist

import com.borzg.domain.model.tv.Tv
import com.borzg.towatchlist.utils.formatToUsDollars
import com.borzg.towatchlist.utils.getDate
import com.borzg.towatchlist.utils.getYearFromDate

class TvWatchListItemViewModel(private val tv: Tv) {

    val name: String
        get() = tv.name

    val originalName: String
        get() = tv.originalName

    val firstAirDate: String
        get() = tv.firstAirDate.getYearFromDate()

    val lastAirDate: String
        get() = tv.lastAirDate.getYearFromDate()

    val numberOfEpisodes: String
        get() = tv.numberOfEpisodes.toString()

    val originalLanguage: String
        get() = tv.originalLanguage

    val addTime: Long?
        get() = tv.addTime

    val isTvWatched: Boolean
        get() = tv.isWatched

    val runtime: String
        get() = tv.episode_run_time.toString()

    val posterPath: String
        get() = tv.posterPath ?: ""
}