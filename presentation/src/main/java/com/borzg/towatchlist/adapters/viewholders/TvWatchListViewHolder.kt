package com.borzg.towatchlist.adapters.viewholders

import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.adapters.OnListItemClickListener
import com.borzg.towatchlist.databinding.LiTvWatchlistBinding

class TvWatchListViewHolder(val binding: LiTvWatchlistBinding) : WatchListViewHolder(binding.root) {

    override fun bind(cinemaElement: CinemaElement, onListItemClickListener: OnListItemClickListener<CinemaElement>) {
        cinemaElement as Movie
    }
}