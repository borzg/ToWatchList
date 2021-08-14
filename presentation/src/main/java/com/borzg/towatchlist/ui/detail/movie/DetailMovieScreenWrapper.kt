package com.borzg.towatchlist.ui.detail.movie

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.BlurTransformation
import com.borzg.domain.model.Movie
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.borzg.towatchlist.ui.detail.DetailCinemaElementScreenWrapper
import com.borzg.towatchlist.utils.BIG_SIZE
import com.borzg.towatchlist.utils.MEDIUM_SIZE
import com.borzg.towatchlist.utils.getYearFromDate
import com.borzg.towatchlist.utils.requestFromImageUrl

object DetailMovieScreenWrapper : DetailCinemaElementScreenWrapper<Movie, DetailMovieViewModel>() {

    @Composable
    override fun DetailScreen(id: Int, viewModel: DetailMovieViewModel) {
        viewModel.setupMovie(id)
        val currentElement by viewModel.movie.collectAsState()
        currentElement?.let {
            DetailCinemaElementScreen(
                cinemaElement = it,
                viewModel = viewModel
            )
        } ?: run {
            PlaceholderCinemaElementScreen()
        }
    }

    @Composable
    override fun PosterBackground(cinemaElement: Movie, modifier: Modifier) {
        Image(
            painter = rememberImagePainter(
                data = requestFromImageUrl(cinemaElement.backdropPath, BIG_SIZE),
                builder = {
                    transformations(BlurTransformation(LocalContext.current))
                    scale(Scale.FILL)
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie backdrop",
            modifier = modifier.fillMaxSize()
        )
    }

    @Composable
    override fun PosterContent(cinemaElement: Movie, modifier: Modifier) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (poster, title, originalTitle, releaseYear) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = requestFromImageUrl(cinemaElement.posterPath, MEDIUM_SIZE)
                ),
                contentDescription = "Movie poster",
                modifier = Modifier
                    .size(
                        width = 100.dp,
                        height = 150.dp
                    )
                    .constrainAs(poster) {
                        top.linkTo(parent.top, 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(title.top)
                    }
                    .clip(RoundedCornerShape(16.dp))
            )

            val isOriginalTitleVisible = cinemaElement.originalTitle != cinemaElement.title
            TextWithShadow(
                text = cinemaElement.title,
                maxLines = 1,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(poster.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(
                        if (isOriginalTitleVisible) originalTitle.top
                        else releaseYear.top
                    )
                }
            )

            if (isOriginalTitleVisible)
                TextWithShadow(
                    text = cinemaElement.originalTitle,
                    maxLines = 1,
                    modifier = Modifier.constrainAs(originalTitle) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(releaseYear.top)
                    }
                )

            TextWithShadow(
                text = cinemaElement.releaseDate.getYearFromDate(),
                maxLines = 1,
                modifier = Modifier.constrainAs(releaseYear) {
                    top.linkTo(
                        if (isOriginalTitleVisible) originalTitle.bottom
                        else title.bottom
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 14.dp)
                }
            )
        }
    }

    @Composable
    override fun DetailsLayout(cinemaElement: Movie, modifier: Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DraggableHandle(modifier = Modifier.padding(top = 8.dp))
            OverviewTitle(
                modifier = Modifier.padding(top = 8.dp),
                cinemaElement = cinemaElement
            )
            Description(
                descriptionText = cinemaElement.overview ?: "",
                modifier = Modifier.padding(top = 8.dp)
            )
            UsersAttitude(
                cinemaElement = cinemaElement,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            MovieFinanceDetails(
                movie = cinemaElement,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
            cinemaElement.imdbId?.let { imdbId ->
                CheckOnImdbButton(
                    imdbLink = BuildConfig.IMDB_ID_URL + imdbId,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }

    @Composable
    override fun AddToWatchlistButton(
        cinemaElement: Movie,
        modifier: Modifier,
        viewModel: DetailMovieViewModel
    ) {
        val context = LocalContext.current
        val addedToWatchListString = stringResource(id = R.string.added_to_watchList)

        FloatingActionButton(
            modifier = modifier,
            onClick = {
                viewModel.addMovieToWatchList(cinemaElement)
                Toast.makeText(
                    context,
                    addedToWatchListString,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Add movie to watchlist"
            )
        }
    }

    @Composable
    fun MovieFinanceDetails(movie: Movie, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
        ) {
            MoneyInfo(
                title = stringResource(id = R.string.budget),
                sum = movie.budget
            )
            MoneyInfo(
                title = stringResource(id = R.string.revenue),
                sum = movie.revenue
            )
        }
    }
}