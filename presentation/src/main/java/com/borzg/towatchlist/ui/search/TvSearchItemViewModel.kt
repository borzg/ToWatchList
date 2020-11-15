package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.utils.getYearFromDate
import com.borzg.towatchlist.utils.getYearFromStringDate


class TvSearchItemViewModel(private val tv: SearchResult.TvSearchResult) {

    val name: String
        get() = tv.name

    val originalName: String
        get() = tv.originalName

    val firstAirDate: String
        get() = tv.firstAirDate.getYearFromDate()

    val posterPath: String
        get() = tv.posterPath ?: ""
}