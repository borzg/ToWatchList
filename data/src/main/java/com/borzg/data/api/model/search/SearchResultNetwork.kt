package com.borzg.data.api.model.search

import com.borzg.domain.model.search.SearchResult

sealed class SearchResultNetwork {
    abstract val id: Int
    abstract val mediaType: String
    abstract var posterPath : String?
}

fun SearchResultNetwork.toDomainOrNull(): SearchResult? =
    when(this) {
        DummySearchResultNetwork -> null
        is MovieSearchResultNetwork -> toDomain()
        is TvSearchResultNetwork -> toDomain()
    }