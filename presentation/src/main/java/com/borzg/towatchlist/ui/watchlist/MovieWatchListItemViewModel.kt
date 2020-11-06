package com.borzg.towatchlist.ui.watchlist

import com.borzg.domain.model.Movie
import com.borzg.towatchlist.utils.formatToUsDollars
import com.borzg.towatchlist.utils.getDate

class MovieWatchListItemViewModel(private val movie: Movie) {

    val title: String
        get() = movie.title

    val releaseDate: String
        get() = movie.releaseDate

    val revenue
        get() = movie.revenue.formatToUsDollars()

    val originalLanguage: String
        get() = movie.originalLanguage

    val addTime: String
        get() = movie.addTime?.getDate() ?: ""

    val budget: String
        get() = movie.budget.formatToUsDollars()

    val isMovieWatched: Boolean
        get() = movie.isWatched

    val runtime: String
        get() = movie.runtime.toString()

    val posterPath: String
        get() = movie.posterPath ?: ""
}