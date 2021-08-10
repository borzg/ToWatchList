package com.borzg.data.api.model

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.tv.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeNetwork(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("production_code") val productionCode: String?,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : DomainMapper<Episode> {

    override fun toDomain(): Episode = Episode(
        airDate = airDate,
        episodeNumber = episodeNumber,
        name = name,
        overview = overview,
        id = id,
        productionCode = productionCode,
        seasonNumber = seasonNumber,
        stillPath = stillPath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}