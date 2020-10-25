package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.towatchlist.adapters.OnSearchItemClickListener

class MovieItemViewModel(private val movie: MovieSearchResult, private val onSearchItemClickListener: OnSearchItemClickListener) {

    val title: String
        get() = movie.title

    val runtime: String
        get() = movie.runtime.toString()

    val posterPath: String
        get() = movie.posterPath ?: ""
}