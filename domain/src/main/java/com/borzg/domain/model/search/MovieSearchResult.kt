package com.borzg.domain.model.search

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    @SerializedName("original_title") val original_title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
) : SearchResult()