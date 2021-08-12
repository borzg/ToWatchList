package com.borzg.data.api.model

import androidx.room.Entity
import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Movie
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieNetwork(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("budget") val budget: Long,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Long,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("vote_count") val vote_count: Int
) : DomainMapper<Movie> {
    @SerializedName("production_countries")
    var productionCountries: List<CountryNetwork>? = null

    override fun toDomain(): Movie =
        Movie(
            id = id,
            backdropPath = backdrop_path,
            budget = budget,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = original_title.sanitize(),
            overview = overview?.sanitize(),
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            title = title.sanitize(),
            voteAverage = vote_average,
            voteCount = vote_count,
            productionCountries = productionCountries?.map { it.toDomain() } ?: emptyList()
        )

    private fun String.sanitize(): String =
        replace("--", '\u2014'.toString())
}