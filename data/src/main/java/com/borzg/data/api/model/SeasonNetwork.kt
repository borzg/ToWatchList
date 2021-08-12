package com.borzg.data.api.model

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.tv.Season
import com.google.gson.annotations.SerializedName

data class SeasonNetwork(
    @SerializedName("_id") val _id: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episodes") val episodes: List<EpisodeNetwork>?,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("season_number") val seasonNumber: Int
) : DomainMapper<Season> {

    override fun toDomain(): Season = Season(
        id = id,
        airDate = airDate ?: "",
        episodes = episodes?.map { it.toDomain() } ?: emptyList(),
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber
    )
}