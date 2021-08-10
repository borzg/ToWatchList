package com.borzg.domain.model.tv

data class Season (
    val id : Int,
    val air_date : String,
    val episodes : List<Episode>,
    val name : String,
    val overview : String,
    val poster_path : String?,
    val season_number : Int
)