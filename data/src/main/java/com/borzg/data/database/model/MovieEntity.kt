package com.borzg.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
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
    var addTime: Long?,
    var isDisplayedInWatchList: Boolean,
    var isWatched: Boolean,
    var watchedAt: Long?,
) : DomainMapper<Movie> {
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var poster: ByteArray? = null

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var backdrop: ByteArray? = null

    override fun toDomain(): Movie =
        Movie(
            id = id,
            backdropPath = backdrop_path,
            budget = budget,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = original_title,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            title = title,
            voteAverage = vote_average,
            voteCount = vote_count,
            poster = poster,
            backdrop = backdrop
        ).apply {
            addTime = this@MovieEntity.addTime
            isDisplayedInWatchList = this@MovieEntity.isDisplayedInWatchList
            isWatched = this@MovieEntity.isWatched
            watchedAt = this@MovieEntity.watchedAt
        }
}

fun Movie.toEntity() = MovieEntity(
    id = id,
    backdrop_path = backdropPath,
    budget = budget,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    original_title = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    title = title,
    vote_average = voteAverage,
    vote_count = voteCount,
    isDisplayedInWatchList = isDisplayedInWatchList ?: false,
    addTime = addTime,
    isWatched = isWatched,
    watchedAt = watchedAt
).apply {
    poster = this@toEntity.poster
    backdrop = this@toEntity.backdrop
}