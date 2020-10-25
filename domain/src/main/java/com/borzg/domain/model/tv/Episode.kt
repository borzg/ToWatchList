package com.borzg.domain.model.tv

import com.google.gson.annotations.SerializedName

data class Episode (
	@SerializedName("air_date") val airDate : String,
	@SerializedName("episode_number") val episodeNumber : Int,
	@SerializedName("name") val name : String,
	@SerializedName("overview") val overview : String,
	@SerializedName("id") val id : Int,
	@SerializedName("production_code") val productionCode : String?,
	@SerializedName("season_number") val seasonNumber : Int,
	@SerializedName("still_path") val stillPath : String?,
	@SerializedName("vote_average") val voteAverage : Double,
	@SerializedName("vote_count") val voteCount : Int
)