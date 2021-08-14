package com.borzg.towatchlist.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.borzg.towatchlist.R

sealed class Screen(
    val route: String,
    @StringRes val markerStringId: Int
) {
    sealed class BottomNavigation(
        route: String,
        @StringRes markerStringId: Int,
        @DrawableRes val iconRes: Int
    ): Screen(route, markerStringId) {
        object WatchList: BottomNavigation("WatchList", R.string.watchlist, R.drawable.ic_list_24)
        object Search: BottomNavigation("Search", R.string.search, R.drawable.ic_search_24)
    }

    object MovieDetails: Screen("MovieDetails", -1)
    object TvDetails: Screen("TvDetails", -1)
}
