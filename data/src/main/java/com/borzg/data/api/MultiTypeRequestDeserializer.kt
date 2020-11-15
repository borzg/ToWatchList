package com.borzg.data.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.borzg.domain.model.common.TmdbResponse
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.SearchResult.*
import com.google.gson.*
import java.lang.IllegalStateException
import java.lang.reflect.Type

class MultiTypeRequestDeserializer : JsonDeserializer<SearchResult> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchResult {
        val jsonObject = json?.asJsonObject
        if (jsonObject != null && context != null) {
            when (jsonObject.get("media_type").asString) {
                "movie" -> return context.deserialize(jsonObject, MovieSearchResult::class.java)
                "tv" -> return context.deserialize(jsonObject, TvSearchResult::class.java)
                "person" -> return DummySearchResult
            }
        }
        throw IllegalStateException("MultiTypeRequestDeserializer: Not correct type")
    }

}