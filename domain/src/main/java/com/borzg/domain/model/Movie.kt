package com.borzg.domain.model

import com.borzg.domain.model.common.CinemaElement

data class Movie(
    override val id: Int,
    val backdrop_path: String?,
    val budget: Long,
    val imdbId: String?,
    val originalLanguage: String,
    val original_title: String,
    val overview: String?,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,
    val productionCountries: List<Country> = emptyList(),
    val poster: ByteArray? = null,
    val backdrop: ByteArray? = null
) : CinemaElement()