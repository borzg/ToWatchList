package com.borzg.towatchlist.ui.detail.tv

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
import com.borzg.domain.model.Tv
import com.borzg.towatchlist.R
import com.borzg.towatchlist.ui.detail.DetailCinemaElementScreenWrapper
import com.borzg.towatchlist.utils.BIG_SIZE
import com.borzg.towatchlist.utils.MEDIUM_SIZE
import com.borzg.towatchlist.utils.formatYearsPeriod
import com.borzg.towatchlist.utils.requestFromImageUrl

object DetailTvScreenWrapper : DetailCinemaElementScreenWrapper<Tv, DetailTvViewModel>() {

    @Composable
    override fun DetailScreen(id: Int, viewModel: DetailTvViewModel) {
        viewModel.setupTv(id)
        val currentElement by viewModel.tv.collectAsState(initial = null)
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
    override fun PosterBackground(cinemaElement: Tv, modifier: Modifier) {
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
            contentDescription = "Tv backdrop",
            modifier = modifier.fillMaxSize()
        )
    }

    @Composable
    override fun PosterContent(cinemaElement: Tv, modifier: Modifier) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (poster, title, originalTitle, releaseYear) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = requestFromImageUrl(cinemaElement.posterPath, MEDIUM_SIZE)
                ),
                contentDescription = "Tv poster",
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

            val isOriginalTitleVisible = cinemaElement.originalName != cinemaElement.name
            TextWithShadow(
                text = cinemaElement.name,
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
                    text = cinemaElement.originalName,
                    maxLines = 1,
                    modifier = Modifier.constrainAs(originalTitle) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(releaseYear.top)
                    }
                )

            TextWithShadow(
                text = formatYearsPeriod(
                    cinemaElement.firstAirDate,
                    cinemaElement.lastAirDate,
                    cinemaElement.inProduction
                ),
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
    override fun DetailsLayout(cinemaElement: Tv, modifier: Modifier) {
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
        }
    }

    @Composable
    override fun AddToWatchlistButton(
        cinemaElement: Tv,
        modifier: Modifier,
        viewModel: DetailTvViewModel
    ) {
        val context = LocalContext.current
        val addedToWatchListString = stringResource(id = R.string.added_to_watchList)

        FloatingActionButton(
            modifier = modifier,
            onClick = {
                viewModel.addTvToWatchList(cinemaElement)
                Toast.makeText(
                    context,
                    addedToWatchListString,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Add tv to watchlist"
            )
        }
    }

}