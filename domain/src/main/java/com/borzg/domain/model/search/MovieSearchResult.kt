package com.borzg.domain.model.search

data class MovieSearchResult(
    override val id: Int,
    override val mediaType: String,
    override var posterPath: String?,
    val original_title: String,
    val title: String,
    val vote_average: Double,
    val release_date: String?,
    val backdrop_path: String?,
    val vote_count: Int
) : SearchResult()