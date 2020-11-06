package com.borzg.towatchlist.adapters.viewholders

import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.databinding.LiMovieWatchlistBinding
import com.borzg.towatchlist.ui.watchlist.MovieWatchListItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl
import kotlinx.coroutines.*

class MovieWatchListViewHolder(private val binding: LiMovieWatchlistBinding) :
    WatchListViewHolder(binding.root) {

    override fun bind(
        cinemaElement: CinemaElement,
        onListItemClickListener: (CinemaElement) -> Unit,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
    ) {
        val vm = MovieWatchListItemViewModel(cinemaElement as Movie)
        with(binding) {
            movieTitle.text = vm.title
            releaseDate.text = vm.releaseDate
            this@MovieWatchListViewHolder.bindWatchStateButton(
                watchStateButton,
                cinemaElement,
                onAddButtonClickListener
            )
            loadImageFromUrl(vm.posterPath, poster)
            param1.text = vm.budget
            param2.text = vm.revenue
            param3.text = vm.originalLanguage
            param4.text = vm.addTime
            root.setOnClickListener {
                onListItemClickListener.invoke(cinemaElement)
            }
        }
    }
}