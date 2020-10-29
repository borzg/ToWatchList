package com.borzg.domain.model.search

import com.google.gson.annotations.SerializedName

open class SearchResult {
    @SerializedName("media_type")
    var media_type: String? = null

    @SerializedName("id")
    var id : Int = -1
}