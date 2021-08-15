package com.borzg.domain.model

import com.borzg.domain.model.common.Country

data class Movie(
    override val id: Int,
    val backdropPath: String?,
    val budget: Long,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    val productionCountries: List<Country> = emptyList(),
    val poster: ByteArray? = null,
    val backdrop: ByteArray? = null,
    override val addTime: Long? = null,
    override val isDisplayedInWatchList: Boolean? = null,
    override val isWatched: Boolean = false,
    override val watchedAt: Long? = null
) : CinemaElement()