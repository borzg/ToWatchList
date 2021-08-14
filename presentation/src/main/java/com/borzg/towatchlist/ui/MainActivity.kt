package com.borzg.towatchlist.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import coil.annotation.ExperimentalCoilApi
import com.borzg.towatchlist.navigation.Screen
import com.borzg.towatchlist.ui.detail.movie.DetailMovieScreenWrapper
import com.borzg.towatchlist.ui.detail.tv.DetailTvScreenWrapper
import com.borzg.towatchlist.ui.search.SearchScreen
import com.borzg.towatchlist.ui.theme.ToWatchListTheme
import com.borzg.towatchlist.ui.watchlist.WatchlistScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToWatchListTheme {
                val navController = rememberNavController()
                val items = listOf(
                    Screen.BottomNavigation.WatchList,
                    Screen.BottomNavigation.Search
                )

                AppScreen(navController = navController, bottomNavigationItems = items)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalCoilApi
    @Composable
    fun AppScreen(
        navController: NavHostController,
        bottomNavigationItems: List<Screen.BottomNavigation>
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    bottomNavigationItems.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.iconRes),
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(screen.markerStringId)) },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == screen.route
                            } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.BottomNavigation.WatchList.route,
                Modifier.padding(innerPadding)
            ) {
                composable(Screen.BottomNavigation.WatchList.route) {
                    WatchlistScreen(
                        navController,
                        viewModel = hiltViewModel()
                    )
                }
                composable(Screen.BottomNavigation.Search.route) {
                    SearchScreen(
                        navController,
                        viewModel = hiltViewModel()
                    )
                }
                composable(
                    route = "${Screen.MovieDetails.route}/{movieId}",
                    arguments = listOf(
                        navArgument("movieId") {
                            type = NavType.IntType
                        }
                    )
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt("movieId")?.let { movieId ->
                        DetailMovieScreenWrapper.DetailScreen(
                            movieId,
                            viewModel = hiltViewModel()
                        )
                    }
                }
                composable(
                    route = "${Screen.TvDetails.route}/{tvId}",
                    arguments = listOf(
                        navArgument("tvId") {
                            type = NavType.IntType
                        }
                    )
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getInt("tvId")?.let { tvId ->
                        DetailTvScreenWrapper.DetailScreen(
                            tvId,
                            viewModel = hiltViewModel()
                        )
                    }
                }
            }
        }
    }
}
