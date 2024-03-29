package com.borzg.towatchlist.ui.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.R
import com.borzg.towatchlist.navigation.Screen
import com.borzg.towatchlist.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private enum class SearchInfoState {
    NO_QUERY,
    NOTHING_FOUND
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel()
) {
    val query by viewModel.searchQuery.collectAsState()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    Column {
        SearchToolbar(
            query = query,
            onNewQuery = { newQuery ->
                viewModel.setNewQuery(newQuery)
            }
        )

        val searchInfoState: SearchInfoState? = when {
            query.isBlank() -> SearchInfoState.NO_QUERY
            searchResults.loadState.refresh is LoadState.NotLoading
                    && searchResults.loadState.append.endOfPaginationReached
                    && searchResults.itemCount == 0 -> SearchInfoState.NOTHING_FOUND
            else -> null
        }

        searchInfoState?.let {
            SearchInfoLayout(it)
        } ?: run {
            SearchList(
                searchResults = searchResults,
                goToDetailMovieScreen = { movieSearchResult ->
                    navController.navigate("${Screen.MovieDetails.route}/${movieSearchResult.id}")
                },
                goToDetailTvScreen = { tvSearchResult ->
                    navController.navigate("${Screen.TvDetails.route}/${tvSearchResult.id}")
                }
            )
        }
    }
}

@Composable
private fun SearchToolbar(
    query: String,
    onNewQuery: (newQuery: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextField(
            value = query,
            onValueChange = onNewQuery,
            placeholder = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = "Search movies and tv series icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun DataLoadingProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val backgroundColor = MaterialTheme.colors.background
        val circleRadius = 20.dp
        val circleRadiusPx = with(LocalDensity.current) { circleRadius.toPx() }
        CircularProgressIndicator(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape
                )
                .size(circleRadius * 2)
                .drawBehind {
                    drawCircle(
                        color = backgroundColor,
                        radius = circleRadiusPx,
                    )
                }
                .padding(circleRadius / 3f)
        )
    }
}

@Composable
private fun SearchInfoLayout(
    searchInfoState: SearchInfoState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when(searchInfoState) {
                SearchInfoState.NO_QUERY -> stringResource(id = R.string.no_search_text)
                SearchInfoState.NOTHING_FOUND -> stringResource(id = R.string.nothing_found)
            },
            textAlign = TextAlign.Center
        )
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
private fun SearchList(
    searchResults: LazyPagingItems<SearchResult>,
    goToDetailMovieScreen: (MovieSearchResult) -> Unit,
    goToDetailTvScreen: (TvSearchResult) -> Unit
) {
    Box {
        LazyColumn {
            itemsIndexed(searchResults) { index, searchResult ->
                when (searchResult) {
                    is MovieSearchResult -> MovieSearchListItem(
                        movieSearchResult = searchResult,
                        onMovieClick = { result ->
                            goToDetailMovieScreen(result)
                        },
                        shouldDrawTopLine = index != 0
                    )
                    is TvSearchResult -> TvSearchListItem(
                        tvSearchResult = searchResult,
                        onTvClick = { result ->
                            goToDetailTvScreen(result)
                        },
                        shouldDrawTopLine = index != 0
                    )
                    null -> return@itemsIndexed
                }
            }
        }

        if (searchResults.loadState.refresh is LoadState.Loading)
            DataLoadingProgress(
                modifier = Modifier
                    .fillMaxWidth()
            )
    }
}

@Composable
private fun MovieSearchListItem(
    movieSearchResult: MovieSearchResult,
    onMovieClick: (MovieSearchResult) -> Unit,
    shouldDrawTopLine: Boolean
) {
    Column(
        modifier = Modifier
            .clickable {
                onMovieClick(movieSearchResult)
            }
            .background(
                color = MaterialTheme.colors.background
            )
    ) {
        if (shouldDrawTopLine)
            ItemTopLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Poster(
                searchResult = movieSearchResult
            )

            MovieSearchInfo(
                movieSearchResult = movieSearchResult,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun TvSearchListItem(
    tvSearchResult: TvSearchResult,
    onTvClick: (TvSearchResult) -> Unit,
    shouldDrawTopLine: Boolean
) {
    Column(
        modifier = Modifier
            .clickable {
                onTvClick(tvSearchResult)
            }
            .background(
                color = MaterialTheme.colors.background
            )
    ) {
        if (shouldDrawTopLine)
            ItemTopLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Poster(
                searchResult = tvSearchResult
            )

            TvSearchInfo(
                tvSearchResult = tvSearchResult,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun ItemTopLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}

@Composable
private fun Poster(searchResult: SearchResult, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(
                    data = requestFromImageUrl(
                        when (searchResult) {
                            is MovieSearchResult -> searchResult.posterPath
                            is TvSearchResult -> searchResult.posterPath
                        }, MEDIUM_SIZE
                    )
                )
                .placeholder(R.drawable.cinema_dummy)
                .error(R.drawable.cinema_dummy)
                .build()
        ),
        contentDescription = "Poster",
        modifier = modifier
            .size(
                width = 100.dp,
                height = 150.dp
            )
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
private fun MovieSearchInfo(
    movieSearchResult: MovieSearchResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(150.dp)
    ) {
        Text(
            text = movieSearchResult.title,
            fontWeight = FontWeight.Bold
        )
        if (movieSearchResult.originalTitle != movieSearchResult.title)
            Text(text = movieSearchResult.originalTitle)
        Text(text = movieSearchResult.releaseDate.getYearFromDate())
    }
}

@Composable
private fun TvSearchInfo(
    tvSearchResult: TvSearchResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(150.dp)
    ) {
        Text(
            text = tvSearchResult.name,
            fontWeight = FontWeight.Bold
        )
        if (tvSearchResult.originalName != tvSearchResult.name)
            Text(text = tvSearchResult.originalName)
        Text(text = tvSearchResult.firstAirDate.getYearFromDate())
    }
}