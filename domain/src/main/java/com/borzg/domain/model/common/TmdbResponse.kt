package com.borzg.domain.model.common

import com.google.gson.annotations.SerializedName

data class TmdbResponse<T> (
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("results") val results : List<T>
)