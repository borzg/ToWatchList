package com.borzg.data.api

import android.util.Log
import com.borzg.data.api.model.search.DummySearchResultNetwork
import com.borzg.data.api.model.search.MovieSearchResultNetwork
import com.borzg.data.api.model.search.SearchResultNetwork
import com.borzg.data.api.model.search.TvSearchResultNetwork
import com.google.gson.*
import java.lang.IllegalStateException
import java.lang.reflect.Type

class MultiTypeRequestDeserializer : JsonDeserializer<SearchResultNetwork> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchResultNetwork {
        val jsonObject = json?.asJsonObject
        if (jsonObject != null && context != null) {
            when (jsonObject.get("media_type").asString) {
                "movie" -> return context.deserialize(jsonObject, MovieSearchResultNetwork::class.java)
                "tv" -> return context.deserialize(jsonObject, TvSearchResultNetwork::class.java)
                "person" -> return DummySearchResultNetwork
            }
        }
        throw IllegalStateException("MultiTypeRequestDeserializer: Not correct type")
    }

}