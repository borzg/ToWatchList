package com.borzg.domain.model.search

import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName

sealed class SearchResult {
    abstract val id: Int
    abstract val media_type: String
    abstract var posterPath : String?

    data class MovieSearchResult(
        @SerializedName("id") override val id: Int,
        @SerializedName("media_type") override val media_type: String,
        @SerializedName("poster_path") override var posterPath: String?,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val vote_average: Double,
        @SerializedName("release_date") val release_date : String?,
        @SerializedName("backdrop_path") val backdrop_path : String?,
        @SerializedName("vote_count") val vote_count: Int
    ) : SearchResult()

    data class TvSearchResult (
        @SerializedName("id") override val id: Int,
        @SerializedName("media_type") override val media_type: String,
        @SerializedName("poster_path") override var posterPath: String?,
        @SerializedName("first_air_date") val firstAirDate : String?,
        @SerializedName("name") val name : String,
        @SerializedName("original_name") val originalName : String,
        @SerializedName("popularity") val popularity : Float,
        @SerializedName("backdrop_path") val backdrop_path : String?,
        @SerializedName("vote_average") val voteAverage : Float,
        @SerializedName("vote_count") val voteCount : Int
    ) : SearchResult()

    /**
     * Stub class for a person type in multi search.
     * Filtered at the pagination stage
     */
    object DummySearchResult : SearchResult() {
        override val id: Int = -1
        override val media_type: String = "Stub type"
        override var posterPath: String? = ""
    }
}