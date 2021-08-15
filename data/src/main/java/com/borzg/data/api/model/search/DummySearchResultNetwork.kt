package com.borzg.data.api.model.search

import androidx.annotation.Keep

@Keep
object DummySearchResultNetwork : SearchResultNetwork() {
    override val id: Int = -1
    override val mediaType: String = "Stub type"
    override var posterPath: String? = ""
}