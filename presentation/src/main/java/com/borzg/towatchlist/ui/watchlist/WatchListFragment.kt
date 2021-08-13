package com.borzg.towatchlist.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transition.CrossfadeTransition
import com.borzg.domain.model.CinemaElement
import com.borzg.domain.model.Movie
import com.borzg.domain.model.Tv
import com.borzg.towatchlist.R
import com.borzg.towatchlist.ui.theme.ToWatchListTheme
import com.borzg.towatchlist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    @ExperimentalCoilApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                ToWatchListTheme {
                    WatchlistScreen()
                }
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun WatchlistScreen(
        viewModel: WatchlistViewModel = viewModel()
    ) {
        val toolbarHeight = 250.dp
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
        var toolbarOffsetHeightPx by rememberSaveable { mutableStateOf(0f) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx + delta
                    toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            val allTimeViewsCount by viewModel.numberOfViewsForAllTime.collectAsState()
            val monthViewsCount by viewModel.numberOfViewsForMonth.collectAsState()
            val weekViewsCount by viewModel.numberOfViewsForWeek.collectAsState()
            StatisticToolbar(
                allTimeViewsCount = allTimeViewsCount,
                monthViewsCount = monthViewsCount,
                weekViewsCount = weekViewsCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeight)
                    .offset {
                        IntOffset(x = 0, y = (toolbarOffsetHeightPx / 2f).roundToInt())
                    }
            )

            WatchList(
                viewModel = viewModel,
                toolbarHeight = toolbarHeight
            )
        }
    }

    @Composable
    fun StatisticToolbar(
        allTimeViewsCount: Int,
        monthViewsCount: Int,
        weekViewsCount: Int,
        modifier: Modifier = Modifier
    ) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (allViews, monthViews, weekViews, background) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.cosmos),
                contentDescription ="Statistics background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(background) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            NumberWithDescription(
                number = monthViewsCount,
                description = stringResource(id = R.string.views_for_month),
                textSize = 26f.sp,
                modifier = Modifier.constrainAs(monthViews) {
                    start.linkTo(parent.start, 32.dp)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.7f
                    )
                    end.linkTo(allViews.start)
                }
            )
            NumberWithDescription(
                number = allTimeViewsCount,
                description = stringResource(id = R.string.views_for_all_time),
                textSize = 46f.sp,
                modifier = Modifier.constrainAs(allViews) {
                    start.linkTo(monthViews.end)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.3f
                    )
                    end.linkTo(weekViews.start)
                }
            )
            NumberWithDescription(
                number = weekViewsCount,
                description = stringResource(id = R.string.views_for_week),
                textSize = 26f.sp,
                modifier = Modifier.constrainAs(weekViews) {
                    start.linkTo(allViews.end)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.7f
                    )
                    end.linkTo(parent.end, 32.dp)
                }
            )
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun WatchList(
        modifier: Modifier = Modifier,
        toolbarHeight: Dp,
        viewModel: WatchlistViewModel = viewModel()
    ) {
        val cinemaElements by viewModel.getContentFromWatchList()
            .collectAsState(initial = emptyList())

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = toolbarHeight),
            content = {
                itemsIndexed(cinemaElements) { index, item ->
                    when (item) {
                        is Movie -> MovieListItem(
                            movie = item,
                            shouldDrawTopLine = index != 0,
                            onChangeMovieWatchedState = {
                                viewModel.setWatchedState(
                                    isWatched = !it.isWatched,
                                    cinemaElement = it,
                                )
                            },
                            onMovieClick = {
                                findNavController().navigate(
                                    WatchListFragmentDirections.actionWatchListFragmentToDetailMovieFragment(
                                        it.id,
                                        it.title,
                                        it.originalTitle,
                                        it.posterPath ?: "",
                                        it.releaseDate,
                                        it.backdropPath ?: ""
                                    )
                                )
                            }
                        )
                        is Tv -> TvListItem(
                            tv = item,
                            shouldDrawTopLine = index != 0,
                            onChangeTvWatchedState = {
                                viewModel.setWatchedState(
                                    isWatched = !it.isWatched,
                                    cinemaElement = it
                                )
                            },
                            onTvClick = {
                                findNavController().navigate(
                                    WatchListFragmentDirections.actionWatchListFragmentToDetailTvFragment(
                                        it.id,
                                        it.name,
                                        it.originalName,
                                        it.posterPath ?: "",
                                        it.firstAirDate ?: "",
                                        it.lastAirDate ?: "",
                                        it.backdropPath ?: "",
                                        it.inProduction
                                    )
                                )
                            }
                        )
                    }
                }
            },
        )
    }

    @Composable
    fun NumberWithDescription(
        number: Int,
        description: String,
        textSize: TextUnit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = number.toString(),
                style = LocalTextStyle.current.copy(
                    fontSize = textSize
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }

    @ExperimentalCoilApi
    @Preview
    @Composable
    fun MovieListItem(
        @PreviewParameter(FakeMoviePreviewProvider::class) movie: Movie,
        onMovieClick: (Movie) -> Unit = {},
        onChangeMovieWatchedState: (Movie) -> Unit = {},
        shouldDrawTopLine: Boolean = true
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onMovieClick(movie)
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
                    cinemaElement = movie
                )

                MovieShortInfo(
                    movie = movie,
                    modifier = Modifier.padding(start = 8.dp),
                    onChangeMovieWatchStateClick = {
                        onChangeMovieWatchedState(it)
                    }
                )
            }

            AddedAt(
                cinemaElement = movie,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
            )
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun TvListItem(
        tv: Tv,
        onChangeTvWatchedState: (Tv) -> Unit,
        onTvClick: (Tv) -> Unit,
        shouldDrawTopLine: Boolean
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onTvClick(tv)
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
                    cinemaElement = tv
                )

                TvShortInfo(
                    tv = tv,
                    modifier = Modifier.padding(start = 8.dp),
                    onChangeTvWatchStateClick = {
                        onChangeTvWatchedState(it)
                    }
                )
            }

            AddedAt(
                cinemaElement = tv,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    fun ItemTopLine() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))
    }

    @Composable
    fun AddedAt(cinemaElement: CinemaElement, modifier: Modifier = Modifier) {
        if (cinemaElement.addTime == null) return
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    id = R.string.space_separated,
                    stringResource(id = R.string.added_on),
                    cinemaElement.addTime.millisecondsToTimePeriod(
                        LocalContext.current
                    )
                )
            )
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun Poster(cinemaElement: CinemaElement, modifier: Modifier = Modifier) {
        Image(
            painter = rememberImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = requestFromImageUrl(
                            when (cinemaElement) {
                                is Movie -> cinemaElement.posterPath
                                is Tv -> cinemaElement.posterPath
                            }, MEDIUM_SIZE
                        )
                    )
                    .transition(CrossfadeTransition())
                    .placeholder(R.drawable.cinema_dummy)
                    .build()
            ),
            contentDescription = "Movie poster",
            modifier = modifier
                .size(
                    width = 100.dp,
                    height = 150.dp
                )
                .clip(RoundedCornerShape(8.dp))
        )
    }

    @Composable
    fun MovieShortInfo(
        movie: Movie,
        onChangeMovieWatchStateClick: (Movie) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.height(150.dp)
        ) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold
            )
            if (movie.originalTitle != movie.title)
                Text(text = movie.originalTitle)
            Text(text = movie.releaseDate.getYearFromDate())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (movie.isWatched) stringResource(id = R.string.viewed)
                        else stringResource(id = R.string.mark_as_viewed),
                    modifier = Modifier
                        .clickable {
                            onChangeMovieWatchStateClick(movie)
                        }
                        .padding(4.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }

    @Composable
    fun TvShortInfo(
        tv: Tv,
        onChangeTvWatchStateClick: (Tv) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.height(150.dp)
        ) {
            Text(
                text = tv.name,
                fontWeight = FontWeight.Bold
            )
            if (tv.originalName != tv.name)
                Text(text = tv.originalName)
            val dates = when {
                tv.firstAirDate != null && tv.lastAirDate != null -> "${tv.firstAirDate.getYearFromDate()}-${tv.lastAirDate.getYearFromDate()}"
                tv.firstAirDate != null && tv.lastAirDate == null -> "${tv.firstAirDate.getYearFromDate()}-..."
                else -> null
            }
            dates?.let {
                Text(text = it)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (tv.isWatched) stringResource(id = R.string.viewed)
                        else stringResource(id = R.string.mark_as_viewed),
                    modifier = Modifier
                        .clickable {
                            onChangeTvWatchStateClick(tv)
                        }
                        .padding(4.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}