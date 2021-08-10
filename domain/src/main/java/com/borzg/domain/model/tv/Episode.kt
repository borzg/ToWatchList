package com.borzg.domain.model.tv

data class Episode (
	val id : Int,
	val airDate : String,
	val episodeNumber : Int,
	val name : String,
	val overview : String,
	val productionCode : String?,
	val seasonNumber : Int,
	val stillPath : String?,
	val voteAverage : Double,
	val voteCount : Int
)