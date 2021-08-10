package com.borzg.data.api.model.search

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.search.TvSearchResult
import com.google.gson.annotations.SerializedName

data class TvSearchResultNetwork(
    @SerializedName("id") override val id: Int,
    @SerializedName("media_type") override val mediaType: String,
    @SerializedName("poster_path") override var posterPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
) : DomainMapper<TvSearchResult>, SearchResultNetwork() {

    override fun toDomain(): TvSearchResult =
        TvSearchResult(
            id = id,
            mediaType = mediaType,
            posterPath = posterPath,
            firstAirDate = firstAirDate,
            name = name,
            originalName = originalName,
            popularity = popularity,
            backdrop_path = backdrop_path,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
}