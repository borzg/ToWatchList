package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.MovieSearchResult

class MovieSearchItemViewModel(private val movie: MovieSearchResult) {

    val title: String
        get() = movie.title

    val runtime: String
        get() = movie.runtime.toString()

    val posterPath: String
        get() = movie.posterPath ?: ""
}