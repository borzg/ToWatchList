package com.borzg.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Person (
	@SerializedName("id")
	@PrimaryKey
	val id : Int,
	@SerializedName("birthday") val birthday : String?,
	@SerializedName("deathday") val deathday : String?,
	@SerializedName("name") val name : String,
	@SerializedName("gender") val gender : Int,
	@SerializedName("biography") val biography : String,
	@SerializedName("popularity") val popularity : Double,
	@SerializedName("place_of_birth") val placeOfBirth : String?,
	@SerializedName("profile_path") val profilePath : String?,
	@SerializedName("adult") val adult : Boolean,
	@SerializedName("imdb_id") val imdbId : String
)