package com.borzg.data.api.model

import com.borzg.data.commons.DomainMapper
import com.borzg.domain.model.Country
import com.google.gson.annotations.SerializedName

data class CountryNetwork(
	@SerializedName("iso_3166_1") val iso_3166_1 : String,
	@SerializedName("name") val name : String
): DomainMapper<Country> {
	override fun toDomain(): Country = Country(
		iso_3166_1 = iso_3166_1,
		name = name
	)
}