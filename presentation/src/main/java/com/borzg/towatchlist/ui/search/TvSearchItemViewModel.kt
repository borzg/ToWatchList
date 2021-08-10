package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.utils.getYearFromDate

class TvSearchItemViewModel(private val tv: TvSearchResult) {

    val name: String
        get() = tv.name

    val originalName: String
        get() = tv.originalName

    val firstAirDate: String
        get() = tv.firstAirDate.getYearFromDate()

    val posterPath: String
        get() = tv.posterPath ?: ""
}