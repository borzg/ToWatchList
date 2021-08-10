package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.towatchlist.utils.getYearFromDate

class MovieSearchItemViewModel(private val movie: MovieSearchResult) {

    val title: String
        get() = movie.title

    val releaseDate: String
        get() = movie.release_date.getYearFromDate()

    val originalTitle: String
        get() = movie.original_title

    val posterPath: String
        get() = movie.posterPath ?: ""

}