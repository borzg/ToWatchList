package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.utils.getYearFromDate
import com.borzg.towatchlist.utils.getYearFromStringDate

class MovieSearchItemViewModel(private val movie: SearchResult.MovieSearchResult) {

    val title: String
        get() = movie.title

    val releaseDate: String
        get() = movie.release_date.getYearFromDate()

    val originalTitle: String
        get() = movie.original_title

    val posterPath: String
        get() = movie.posterPath ?: ""

}