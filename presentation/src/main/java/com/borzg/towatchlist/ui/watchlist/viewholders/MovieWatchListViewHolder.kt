package com.borzg.towatchlist.ui.watchlist.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.LiMovieWatchlistBinding
import com.borzg.towatchlist.ui.watchlist.MovieWatchListItemViewModel
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.loadImageFromUrl
import com.borzg.towatchlist.utils.millisecondsToTimePeriod
import com.borzg.towatchlist.utils.setTextAndVisibility

class MovieWatchListViewHolder(private val binding: LiMovieWatchlistBinding) :
    WatchListViewHolder(binding.root) {

    override fun bind(
        cinemaElement: CinemaElement,
        onListItemClickListener: (CinemaElement) -> Unit,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
    ) {
        val vm = MovieWatchListItemViewModel(cinemaElement as Movie)
        currentCinemaElement = cinemaElement
        with(binding) {
            movieTitle.text = vm.title
            releaseDate.text = vm.releaseDate
            if (vm.originalTitle != vm.title)
                originalTitle.setTextAndVisibility(vm.originalTitle)
            else originalTitle.hideView()
            this@MovieWatchListViewHolder.bindWatchStateButton(
                watchStateButton,
                cinemaElement,
                onAddButtonClickListener
            )
            loadImageFromUrl(vm.posterPath, poster)
            addedAt.text = "${getString(R.string.added_on)} ${vm.addTime.millisecondsToTimePeriod(addedAt.context)}"
            root.setOnClickListener {
                onListItemClickListener.invoke(cinemaElement)
            }
        }
    }
}