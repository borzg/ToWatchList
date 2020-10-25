package com.borzg.domain.model.tv

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Tv (
	@SerializedName("id")
	@PrimaryKey
	val id : Int,
	@SerializedName("backdrop_path") val backdropPath : String?,
	@SerializedName("first_air_date") val firstAirDate : String,
	@SerializedName("in_production") val inProduction : Boolean,
	@SerializedName("last_air_date") val lastAirDate : String,
	@SerializedName("name") val name : String,
	@SerializedName("number_of_episodes") val numberOfEpisodes : Int,
	@SerializedName("number_of_seasons") val numberOfSeasons : Int,
	@SerializedName("original_language") val originalLanguage : String,
	@SerializedName("original_name") val originalName : String,
	@SerializedName("overview") val overview : String,
	@SerializedName("popularity") val popularity : Float,
	@SerializedName("poster_path") val posterPath : String?,
	@SerializedName("type") val type : String,
	@SerializedName("vote_average") val voteAverage : Float,
	@SerializedName("vote_count") val voteCount : Int
) {
	@Ignore @SerializedName("episode_run_time") var episode_run_time : List<Int>? = null
	@Ignore  @SerializedName("languages") val languages : List<String>? = null
	@Ignore  @SerializedName("last_episode_to_air") val lastEpisodeToAir : Episode? = null
	@Ignore  @SerializedName("origin_country") val originCountry : List<String>? = null
	@Ignore  @SerializedName("seasons") val seasons : List<Season>? = null
}