package com.borzg.domain.model.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.borzg.domain.model.Country
import com.borzg.domain.model.tv.Episode
import com.borzg.domain.model.tv.Season
import com.google.gson.annotations.SerializedName

/**
 * Parent class for Movie and Tv
 */
abstract class CinemaElement() {

    abstract val id: Int

    /**
     * Time in which element was added to WatchList
     */
    var addTime: Long? = null

    /**
     * Determines whether element will be displayed in the WatchList
     */
    var isDisplayedInWatchList: Boolean? = null

    /**
     * Determines whether element has been watched
     */
    var isWatched: Boolean = false

    /**
     * Determines time at which element was watched
     */
    var watchedAt: Long? = null

    fun copyCinemaElementParametersFrom(from: CinemaElement) {
        addTime = from.addTime
        isDisplayedInWatchList = from.isDisplayedInWatchList
        isWatched = from.isWatched
        watchedAt = from.watchedAt
    }
}
