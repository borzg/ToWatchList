package com.borzg.domain.model

import androidx.room.*
import com.borzg.domain.model.common.CinemaElement
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie(
        @PrimaryKey @SerializedName("id") override val id: Int,
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
) : CinemaElement() {
        @Ignore @SerializedName("production_countries") var productionCountries: List<Country>? = null
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var poster: ByteArray? = null
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var backdrop: ByteArray? = null
}