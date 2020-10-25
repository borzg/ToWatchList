package com.borzg.domain.model.tv

import com.google.gson.annotations.SerializedName

data class Season (
    @SerializedName("_id") val _id : String?,
    @SerializedName("air_date") val air_date : String,
    @SerializedName("episodes") val episodes : List<Episode>,
    @SerializedName("name") val name : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("id") val id : Int,
    @SerializedName("poster_path") val poster_path : String?,
    @SerializedName("season_number") val season_number : Int
)