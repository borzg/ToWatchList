package com.borzg.domain.model.search

sealed class SearchResult {
    abstract val id: Int
    abstract val mediaType: String
    abstract var posterPath : String?
}