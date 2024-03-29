package com.borzg.data.api.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TmdbResponse<T> (
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("results") val results : List<T>
)