package com.borzg.data.api.model.search

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.search.MovieSearchResult
import com.google.gson.annotations.SerializedName

data class MovieSearchResultNetwork(
    @SerializedName("id") override val id: Int,
    @SerializedName("media_type") override val mediaType: String,
    @SerializedName("poster_path") override var posterPath: String?,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("release_date") val release_date: String?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("vote_count") val vote_count: Int
) : DomainMapper<MovieSearchResult>, SearchResultNetwork() {

    override fun toDomain(): MovieSearchResult =
        MovieSearchResult(
            id = id,
            mediaType = mediaType,
            posterPath = posterPath,
            original_title = original_title,
            title = title,
            vote_average = vote_average,
            release_date = release_date,
            backdrop_path = backdrop_path,
            vote_count = vote_count
        )
}