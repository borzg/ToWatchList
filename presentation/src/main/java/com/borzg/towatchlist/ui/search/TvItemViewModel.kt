package com.borzg.towatchlist.ui.search

import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.adapters.OnListItemClickListener

class TvItemViewModel(private val tv: TvSearchResult) {

    val title: String
        get() = tv.name

    val firstAirDate: String
        get() = tv.firstAirDate

    val posterPath: String
        get() = tv.posterPath ?: ""
}