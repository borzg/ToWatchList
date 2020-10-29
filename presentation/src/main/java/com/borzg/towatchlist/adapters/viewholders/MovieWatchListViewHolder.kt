package com.borzg.towatchlist.adapters.viewholders

import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.adapters.OnListItemClickListener
import com.borzg.towatchlist.databinding.LiMovieWatchlistBinding
import com.borzg.towatchlist.utils.loadImageFromUrl

class MovieWatchListViewHolder(val binding: LiMovieWatchlistBinding) :
    WatchListViewHolder(binding.root) {

    override fun bind(cinemaElement: CinemaElement, onListItemClickListener: OnListItemClickListener<CinemaElement>) {
        val movie = cinemaElement as Movie
        with(binding) {
            movieTitle.text = movie.title
            releaseDate.text = movie.releaseDate
            loadImageFromUrl(movie.posterPath, poster)
            param1.text = movie.budget.toString()
            param2.text = movie.revenue.toString()
            param3.text = movie.originalLanguage
            param4.text = movie.productionCountries.toString()
            root.setOnClickListener {
                onListItemClickListener.onItemClick(movie)
            }
        }
    }
}