package com.borzg.domain.model

data class Person (
	val id : Int,
	val birthday : String?,
	val deathday : String?,
	val name : String,
	val gender : Int,
	val biography : String,
	val popularity : Double,
	val placeOfBirth : String?,
	val profilePath : String?,
	val adult : Boolean,
	val imdbId : String
)