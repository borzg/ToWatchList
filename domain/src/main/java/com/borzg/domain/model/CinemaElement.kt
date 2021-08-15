package com.borzg.domain.model

/**
 * Parent class for Movie and Tv
 */
sealed class CinemaElement {

    abstract val id: Int

    /**
     * Time in which element was added to WatchList
     */
    abstract val addTime: Long?

    /**
     * Determines whether element will be displayed in the WatchList, null if not added
     */
    abstract val isDisplayedInWatchList: Boolean?

    /**
     * Determines whether element has been watched
     */
    abstract val isWatched: Boolean

    /**
     * Determines time at which element was watched
     */
    abstract val watchedAt: Long?

    fun copyLocalCinemaElementParameters(
        addTime: Long? = this.addTime,
        isDisplayedInWatchList: Boolean? = this.isDisplayedInWatchList,
        isWatched: Boolean = this.isWatched,
        watchedAt: Long? = this.watchedAt
    ): CinemaElement = when (this) {
        is Movie -> copy(
            addTime = addTime,
            isDisplayedInWatchList = isDisplayedInWatchList,
            isWatched = isWatched,
            watchedAt = watchedAt
        )
        is Tv -> copy(
            addTime = addTime,
            isDisplayedInWatchList = isDisplayedInWatchList,
            isWatched = isWatched,
            watchedAt = watchedAt
        )
        else -> error("Some bug with exhaustive sealed classes")
    }

    inline fun <reified T : CinemaElement> copyTypedLocalCinemaElementParameters(
        addTime: Long? = this.addTime,
        isDisplayedInWatchList: Boolean? = this.isDisplayedInWatchList,
        isWatched: Boolean = this.isWatched,
        watchedAt: Long? = this.watchedAt
    ): T = copyLocalCinemaElementParameters(
        addTime = addTime,
        isDisplayedInWatchList = isDisplayedInWatchList,
        isWatched = isWatched,
        watchedAt = watchedAt
    ) as T

    inline fun <reified T : CinemaElement> copyCinemaElementParametersFrom(from: CinemaElement): T =
        copyTypedLocalCinemaElementParameters(
            from.addTime, from.isDisplayedInWatchList, from.isWatched, from.watchedAt
        )
}
