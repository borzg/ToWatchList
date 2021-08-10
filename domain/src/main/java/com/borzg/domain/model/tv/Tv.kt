package com.borzg.domain.model.tv

import com.borzg.domain.model.common.CinemaElement

data class Tv (
	override val id : Int,
	val backdropPath : String?,
	val firstAirDate : String?,
	val lastAirDate : String?,
	val inProduction : Boolean,
	val name : String,
	val numberOfEpisodes : Int,
	val numberOfSeasons : Int?,
	val originalLanguage : String,
	val originalName : String,
	val overview : String?,
	val popularity : Float,
	val posterPath : String?,
	val type : String,
	val vote_average : Float,
	val vote_count : Int,
	var episode_run_time : List<Int> = emptyList(),
	val languages : List<String> = emptyList(),
	val lastEpisodeToAir : Episode? = null,
	val originCountry : List<String> = emptyList(),
	val seasons : List<Season> = emptyList()
) : CinemaElement()