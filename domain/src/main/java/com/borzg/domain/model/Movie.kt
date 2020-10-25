package com.borzg.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
        @SerializedName("id")
        @PrimaryKey
        val id: Int,
        @SerializedName("backdrop_path") val backdrop_path: String?,
        @SerializedName("budget") val budget: Int,
        @SerializedName("imdb_id") val imdbId: String?,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("overview") val overview: String?,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("revenue") val revenue: Int,
        @SerializedName("runtime") val runtime: Int?,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val vote_average: Double,
        @SerializedName("vote_count") val vote_count: Int
) {
        @Ignore @SerializedName("production_countries") var productionCountries: List<Country>? = null
}