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
    var addTime: Long? = null,
    var isDisplayedInWatchList: Boolean? = null,
    var isWatched: Boolean = false,
    var watchedAt: Long? = null,
) : DomainMapper<Movie> {
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var poster: ByteArray? = null

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var backdrop: ByteArray? = null

    override fun toDomain(): Movie =
        Movie(
            id = id,
            backdrop_path = backdrop_path,
            budget = budget,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            title = title,
            vote_average = vote_average,
            vote_count = vote_count,
            poster = poster,
            backdrop = backdrop
        )
}

fun Movie.toEntity() = MovieEntity(
    id = id,
    backdrop_path = backdrop_path,
    budget = budget,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count
).apply {
    poster = this@toEntity.poster
    backdrop = this@toEntity.backdrop
}