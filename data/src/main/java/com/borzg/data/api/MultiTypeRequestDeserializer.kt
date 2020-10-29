package com.borzg.data.api

import android.util.Log
import com.borzg.domain.model.search.DummySearchResult
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.IllegalStateException
import java.lang.reflect.Type

class MultiTypeRequestDeserializer : JsonDeserializer<SearchResult> {

    val gson : Gson = Gson()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchResult {
        val jsonObject = json?.asJsonObject

        if (jsonObject != null) {
            when (jsonObject.get("media_type").asString) {
                "movie" -> return gson.fromJson(jsonObject, MovieSearchResult::class.java)
                "tv" -> return gson.fromJson(jsonObject, TvSearchResult::class.java)
                "person" -> return DummySearchResult()
            }
        }
        // TODO exception
        throw IllegalStateException("MultiTypeRequestDeserializer: Not correct type")
    }
}