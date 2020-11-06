package com.borzg.domain.model.common

// Parent class for Movie and Tv
abstract class CinemaElement() {

    abstract val id: Int

    // Time in which element was added to WatchList
    var addTime: Long? = null

    // Determines whether element will be displayed in the WatchList
    var isDisplayed: Boolean? = null

    // Determines whether element has been watched
    var isWatched: Boolean = false
}