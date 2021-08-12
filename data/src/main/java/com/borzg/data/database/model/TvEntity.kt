package com.borzg.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Tv

@Entity(tableName = "tv")
data class TvEntity(
    @PrimaryKey
    val id: Int,
    val backdropPath: String?,
    val firstAirDate: String?,
    val lastAirDate: String?,
    val inProduction: Boolean,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int?,
    val originalLanguage: String,
    val originalName: String,
    val overview: String?,
    val popularity: Float,
    val posterPath: String?,
    val type: String,
    val vote_average: Float,
    val vote_count: Int,
    var addTime: Long? = null,
    var isDisplayedInWatchList: Boolean? = null,
    var isWatched: Boolean = false,
    var watchedAt: Long? = null,
) : DomainMapper<Tv> {

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
        voteCount = vote_count
    )
}

fun Tv.toEntity() = TvEntity(
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
    vote_average = voteAverage,
    vote_count = voteCount
)