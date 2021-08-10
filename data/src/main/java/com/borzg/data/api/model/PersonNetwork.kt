package com.borzg.data.api.model

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Person
import com.google.gson.annotations.SerializedName

data class PersonNetwork(
    @SerializedName("id") val id: Int,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("deathday") val deathday: String?,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("biography") val biography: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("imdb_id") val imdbId: String
) : DomainMapper<Person> {

    override fun toDomain(): Person = Person(
        id = id,
        birthday = birthday,
        deathday = deathday,
        name = name,
        gender = gender,
        biography = biography,
        popularity = popularity,
        placeOfBirth = placeOfBirth,
        profilePath = profilePath,
        adult = adult,
        imdbId = imdbId
    )
}