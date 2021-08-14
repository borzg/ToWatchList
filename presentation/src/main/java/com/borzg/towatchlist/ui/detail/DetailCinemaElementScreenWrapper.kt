package com.borzg.towatchlist.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import com.borzg.domain.model.CinemaElement
import com.borzg.domain.model.Movie
import com.borzg.domain.model.Tv
import com.borzg.towatchlist.R
import com.borzg.towatchlist.utils.formatToUsDollars
import com.borzg.towatchlist.utils.views.RatingBar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

abstract class DetailCinemaElementScreenWrapper<T : CinemaElement, VM: ViewModel> {

    @Composable
    abstract fun DetailScreen(id: Int, viewModel: VM)

    @Composable
    open fun DetailCinemaElementScreen(cinemaElement: T, viewModel: VM) {
        val toolbarHeight = 300.dp
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
        val offset = rememberSaveable(saver = object : Saver<Animatable<Float, AnimationVector1D>, Float> {
            override fun restore(value: Float): Animatable<Float, AnimationVector1D> {
                return Animatable(initialValue = value)
            }

            override fun SaverScope.save(value: Animatable<Float, AnimationVector1D>): Float {
                return value.value
            }
        }) {
            Animatable(
                initialValue = toolbarHeightPx
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    val decay = splineBasedDecay<Float>(this)
                    coroutineScope {
                        while (true) {
                            val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                            val velocityTracker = VelocityTracker()
                            offset.stop()
                            val startOffset = offset.value
                            awaitPointerEventScope {
                                verticalDrag(pointerId) { change ->
                                    launch {
                                        offset.snapTo(offset.value + change.positionChange().y)
                                    }
                                    velocityTracker.addPosition(
                                        change.uptimeMillis,
                                        change.position
                                    )
                                }
                            }
                            val isDraggingToBottom = offset.value - startOffset > 0

                            val velocity = velocityTracker.calculateVelocity().y
                            val targetOffset = decay.calculateTargetValue(
                                offset.value,
                                velocity
                            )
                            launch {
                                val velocityToTop =
                                    !isDraggingToBottom && targetOffset.absoluteValue > toolbarHeightPx
                                val velocityToBottom =
                                    isDraggingToBottom && targetOffset.absoluteValue > toolbarHeightPx
                                offset.animateTo(
                                    targetValue = when {
                                        velocityToTop -> 0f
                                        velocityToBottom -> toolbarHeightPx
                                        offset.value < toolbarHeightPx / 2 -> 0f
                                        else -> toolbarHeightPx
                                    }
                                )
                            }
                        }
                    }
                }
        ) {
            val offsetY = ((offset.value - toolbarHeightPx) / 2f).roundToInt()
            val crossing = 50
            val detailsLayoutTopPadding = max(0.dp, with(
                LocalDensity.current
            ) {
                min(offset.value.toDp(), toolbarHeight + crossing.dp)
            })

            val posterBackgroundOffset = with(LocalDensity.current) { min(offsetY, 0).toDp() }
            val posterContentOffset =
                with(LocalDensity.current) { min(offsetY, crossing / 2).toDp() }
            val detailsLayoutOffset = max(0.dp, detailsLayoutTopPadding - 12.dp)

            val (posterBackground, posterContent, detailsLayout, addToWatchListButton) = createRefs()

            PosterBackground(
                cinemaElement = cinemaElement,
                modifier = Modifier
                    .height(toolbarHeight + crossing.dp)
                    .constrainAs(posterBackground) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, posterBackgroundOffset)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .scale(
                        min(
                            1.2f,
                            1f + if (offsetY > 0) offsetY.toFloat() / (crossing * 10) else 0f
                        )
                    )
            )

            PosterContent(
                cinemaElement = cinemaElement,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeight)
                    .constrainAs(posterContent) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, posterContentOffset)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 16.dp)
            )

            DetailsLayout(
                cinemaElement = cinemaElement,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(detailsLayout) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, detailsLayoutOffset)
                        end.linkTo(parent.end)
                    }
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(
                            topStart = min(12.dp, detailsLayoutTopPadding),
                            topEnd = min(12.dp, detailsLayoutTopPadding)
                        )
                    )
            )

            val fabSize = 64.dp
            val fabScaleAnimation by animateFloatAsState(
                targetValue =
                if (cinemaElement.isDisplayedInWatchList != true
                    && detailsLayoutTopPadding - 12.dp > fabSize / 2
                ) 1f
                else 0f
            )
            AddToWatchlistButton(
                cinemaElement = cinemaElement,
                modifier = Modifier
                    .constrainAs(addToWatchListButton) {
                        top.linkTo(detailsLayout.top)
                        end.linkTo(detailsLayout.end, 16.dp)
                        bottom.linkTo(detailsLayout.top)
                    }
                    .scale(fabScaleAnimation),
                viewModel = viewModel
            )
        }
    }

    @Composable
    abstract fun PosterBackground(cinemaElement: T, modifier: Modifier)

    @Composable
    abstract fun PosterContent(cinemaElement: T, modifier: Modifier)

    @Composable
    abstract fun DetailsLayout(cinemaElement: T, modifier: Modifier)

    @Composable
    abstract fun AddToWatchlistButton(cinemaElement: T, modifier: Modifier, viewModel: VM)

    @Composable
    open fun PlaceholderCinemaElementScreen() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val toolbarHeight = 300.dp
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeight)
                    .background(
                        color = Color.Gray
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(
                                width = 100.dp,
                                height = 150.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                    TextPlaceholder(
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(
                                width = 90.dp,
                                height = 20.dp
                            )
                    )
                    TextPlaceholder(
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(
                                width = 170.dp,
                                height = 20.dp
                            )
                    )
                    TextPlaceholder(
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(
                                width = 100.dp,
                                height = 20.dp
                            )
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = toolbarHeight - 12.dp
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DraggableHandle(modifier = Modifier.padding(top = 8.dp))
                TextPlaceholder(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(
                            width = 180.dp,
                            height = 32.dp
                        )
                )
                TextPlaceholder(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(
                            width = 160.dp,
                            height = 20.dp
                        )
                )
                TextPlaceholder(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(
                            width = 170.dp,
                            height = 20.dp
                        )
                )
                TextPlaceholder(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(
                            width = 140.dp,
                            height = 20.dp
                        )
                )
            }
        }
    }

    @Composable
    fun TextPlaceholder(color: Color = Color.Gray, modifier: Modifier) {
        Box(
            modifier = modifier
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
    }

    @Composable
    fun DraggableHandle(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .alpha(0.4f)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                )
                .size(24.dp, 2.dp)
        )
    }

    @Composable
    fun TextWithShadow(
        text: String,
        modifier: Modifier = Modifier,
        maxLines: Int = Int.MAX_VALUE,
        blurRadius: Float = 8f
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = modifier,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.copy(
                shadow = Shadow(
                    color = Color.Black,
                    blurRadius = blurRadius
                )
            )
        )
    }

    @Composable
    fun Description(descriptionText: String, modifier: Modifier = Modifier) {
        Text(
            text = descriptionText,
            style = MaterialTheme.typography.body2,
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
        )
    }

    @Composable
    fun VoteCount(count: Int, modifier: Modifier = Modifier) {
        Text(
            text = stringResource(id = R.string.vote_count_with_number, count),
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }

    @Composable
    fun VoteRating(rating: Float, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RatingBar(
                rating = rating,
                modifier = Modifier.size(
                    width = 190.dp,
                    height = 20.dp
                ),
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = rating.toString(),
                style = MaterialTheme.typography.body1,
                color = colorForPerformance(rating),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    @Composable
    fun OverviewTitle(cinemaElement: T, modifier: Modifier = Modifier) {
        Text(
            text = when (cinemaElement as CinemaElement) {
                is Movie -> stringResource(id = R.string.movie_overview)
                is Tv -> stringResource(id = R.string.overview)
            },
            style = MaterialTheme.typography.h5,
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun UsersAttitude(cinemaElement: T, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
        ) {
            VoteCount(
                count = when (val element = cinemaElement as CinemaElement) {
                    is Movie -> element.voteCount
                    is Tv -> element.voteCount
                },
                modifier = Modifier.weight(1f)
            )
            VoteRating(
                rating = when (val element = cinemaElement as CinemaElement) {
                    is Movie -> element.voteAverage
                    is Tv -> element.voteAverage
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            )
        }
    }

    @Composable
    fun MoneyInfo(title: String, sum: Long, modifier: Modifier = Modifier) {
        if (sum == 0L) return
        Row(
            modifier = modifier
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = sum.formatToUsDollars(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun CheckOnImdbButton(imdbLink: String, modifier: Modifier = Modifier) {
        val context = LocalContext.current
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(imdbLink)
                context.startActivity(intent)
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.check_on_imdb),
                color = Color.White
            )
        }
    }

    @Composable
    fun colorForPerformance(performance: Float): Color {
        return when {
            performance < 0 || performance > 10 -> colorResource(id = R.color.primaryTextColor)
            performance < 4 -> colorResource(id = R.color.awfulPerformance)
            performance < 6 -> colorResource(id = R.color.mediumPerformance)
            performance < 8 -> colorResource(id = R.color.goodPerformance)
            else -> colorResource(id = R.color.perfectPerformance)
        }
    }
}