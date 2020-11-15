package com.borzg.towatchlist.ui.watchlist.viewholders

import android.util.Log
import android.view.View
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.tv.Tv
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.LiTvWatchlistBinding
import com.borzg.towatchlist.ui.watchlist.MovieWatchListItemViewModel
import com.borzg.towatchlist.ui.watchlist.TvWatchListItemViewModel
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.loadImageFromUrl
import com.borzg.towatchlist.utils.millisecondsToTimePeriod
import com.borzg.towatchlist.utils.setTextAndVisibility

class TvWatchListViewHolder(val binding: LiTvWatchlistBinding) : WatchListViewHolder(binding.root) {

    override fun bind(
        cinemaElement: CinemaElement,
        onListItemClickListener: (CinemaElement) -> Unit,
        onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
    ) {
        val vm = TvWatchListItemViewModel(cinemaElement as Tv)
        with(binding) {
            tvName.text = vm.name
            firstAirDate.text = vm.firstAirDate
            if (vm.originalName != vm.name)
                originalName.setTextAndVisibility(vm.originalName)
            else originalName.hideView()
            this@TvWatchListViewHolder.bindWatchStateButton(
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