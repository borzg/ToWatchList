package com.borzg.domain.model.search

data class MovieSearchResult(
    override val id: Int,
    override val mediaType: String,
    override var posterPath: String?,
    val originalTitle: String,
    val title: String,
    val vote_average: Double,
    val releaseDate: String?,
    val backdropPath: String?,
    val voteCount: Int
) : SearchResult()