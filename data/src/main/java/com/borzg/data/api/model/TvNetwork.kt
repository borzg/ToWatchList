package com.borzg.data.api.model

import androidx.annotation.Keep
import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Tv
import com.google.gson.annotations.SerializedName

@Keep
data class TvNetwork(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("last_air_date") val lastAirDate: String?,
    @SerializedName("in_production") val inProduction: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("vote_count") val vote_count: Int
) : DomainMapper<Tv> {
    @SerializedName("episode_run_time")
    var episode_run_time: List<Int>? = null
    @SerializedName("languages")
    val languages: List<String>? = null
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeNetwork? = null
    @SerializedName("origin_country")
    val originCountry: List<String>? = null
    @SerializedName("seasons")
    val seasons: List<SeasonNetwork>? = null

    override fun toDomain(): Tv = Tv(
        id = id,
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        inProduction = inProduction,
        name = name,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        type = type,
        voteAverage = vote_average,
        voteCount = vote_count,
        episodeRunTime = episode_run_time ?: emptyList(),
        languages = languages ?: emptyList(),
        lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
        originCountry = originCountry ?: emptyList(),
        seasons = seasons?.map { it.toDomain() } ?: emptyList()
    )
}