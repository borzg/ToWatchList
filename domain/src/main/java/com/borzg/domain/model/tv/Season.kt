package com.borzg.domain.model.tv

data class Season (
    val id : Int,
    val airDate : String,
    val episodes : List<Episode>,
    val name : String,
    val overview : String,
    val posterPath : String?,
    val seasonNumber : Int
)