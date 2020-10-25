package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.adapters.OnSearchItemClickListener

class TvItemViewModel(private val tv: TvSearchResult, private val onSearchItemClickListener: OnSearchItemClickListener) {

    val title: String
        get() = tv.name

    val firstAirDate: String
        get() = tv.firstAirDate

    val posterPath: String
        get() = tv.posterPath ?: ""
}