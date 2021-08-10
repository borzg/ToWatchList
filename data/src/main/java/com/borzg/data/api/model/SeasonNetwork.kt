package com.borzg.data.api.model

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.tv.Season
import com.google.gson.annotations.SerializedName

data class SeasonNetwork(
    @SerializedName("_id") val _id: String?,
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episodes") val episodes: List<EpisodeNetwork>,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("season_number") val season_number: Int
) : DomainMapper<Season> {

    override fun toDomain(): Season = Season(
        id = id,
        air_date = air_date,
        episodes = episodes.map { it.toDomain() },
        name = name,
        overview = overview,
        poster_path = poster_path,
        season_number = season_number
    )
}