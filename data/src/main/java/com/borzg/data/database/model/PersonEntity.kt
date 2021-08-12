package com.borzg.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.common.Person

@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey
    val id: Int,
    val birthday: String?,
    val deathday: String?,
    val name: String,
    val gender: Int,
    val biography: String,
    val popularity: Double,
    val placeOfBirth: String?,
    val profilePath: String?,
    val adult: Boolean,
    val imdbId: String
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