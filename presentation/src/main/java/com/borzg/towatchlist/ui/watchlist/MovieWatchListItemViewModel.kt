package com.borzg.towatchlist.ui.watchlist

import com.borzg.domain.model.Movie
import com.borzg.towatchlist.utils.formatToUsDollars
import com.borzg.towatchlist.utils.getDate
import com.borzg.towatchlist.utils.getYearFromDate

class MovieWatchListItemViewModel(private val movie: Movie) {

    val title: String
        get() = movie.title

    val releaseDate: String
        get() = movie.releaseDate.getYearFromDate()

    val originalTitle: String
        get() = movie.original_title

    val revenue
        get() = movie.revenue.formatToUsDollars()

    val originalLanguage: String
        get() = movie.originalLanguage

    val addTime: Long?
        get() = movie.addTime

    val budget: String
        get() = movie.budget.formatToUsDollars()

    val isMovieWatched: Boolean
        get() = movie.isWatched

    val runtime: String
        get() = movie.runtime.toString()

    val posterPath: String
        get() = movie.posterPath ?: ""
}