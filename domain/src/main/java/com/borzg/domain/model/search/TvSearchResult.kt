package com.borzg.domain.model.search

data class TvSearchResult(
    override val id: Int,
    override val mediaType: String,
    override var posterPath: String?,
    val firstAirDate: String?,
    val name: String,
    val originalName: String,
    val popularity: Float,
    val backdrop_path: String?,
    val voteAverage: Float,
    val voteCount: Int
) : SearchResult()