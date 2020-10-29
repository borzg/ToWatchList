package com.borzg.domain.model.common

// Parent class for Movie and Tv
open class CinemaElement(var elementId: Int) {

    // Time in which element was added to WatchList
    var addTime: Long? = null

    // Determines whether element will be displayed in the sheet
    var isViewed: Boolean? = null
}