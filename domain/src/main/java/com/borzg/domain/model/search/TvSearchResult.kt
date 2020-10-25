package com.borzg.domain.model.search

import com.google.gson.annotations.SerializedName

data class TvSearchResult (
    @SerializedName("first_air_date") val firstAirDate : String,
    @SerializedName("name") val name : String,
    @SerializedName("original_name") val originalName : String,
    @SerializedName("popularity") val popularity : Float,
    @SerializedName("poster_path") val posterPath : String?,
    @SerializedName("vote_average") val voteAverage : Float,
    @SerializedName("vote_count") val voteCount : Int
) : SearchResult()