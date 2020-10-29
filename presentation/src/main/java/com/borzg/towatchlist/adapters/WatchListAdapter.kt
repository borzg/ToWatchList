package com.borzg.towatchlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.tv.Tv
import com.borzg.towatchlist.adapters.viewholders.*
import com.borzg.towatchlist.databinding.LiMovieSearchBinding
import com.borzg.towatchlist.databinding.LiMovieWatchlistBinding
import com.borzg.towatchlist.databinding.LiTvSearchBinding
import com.borzg.towatchlist.databinding.LiTvWatchlistBinding

class WatchListAdapter(val onListItemClickListener: OnListItemClickListener<CinemaElement>): ListAdapter<CinemaElement, WatchListViewHolder>(WatchListItemDiffCallback) {

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is Movie -> MOVIE_TYPE
            is Tv -> TV_TYPE
            else -> throw IllegalStateException("Type of cinemaElement is not yet implemented in function getItemViewType()")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            MOVIE_TYPE -> return MovieWatchListViewHolder(LiMovieWatchlistBinding.inflate(inflater, parent, false))
            TV_TYPE -> return TvWatchListViewHolder(LiTvWatchlistBinding.inflate(inflater, parent, false))
        }
        throw java.lang.IllegalStateException("Wrong viewType")
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(getItem(position), onListItemClickListener)
    }

    object WatchListItemDiffCallback : DiffUtil.ItemCallback<CinemaElement>() {

        override fun areItemsTheSame(oldItem: CinemaElement, newItem: CinemaElement): Boolean {
            return oldItem.elementId == newItem.elementId
        }

        override fun areContentsTheSame(oldItem: CinemaElement, newItem: CinemaElement): Boolean {
            return oldItem.elementId == newItem.elementId
        }

    }
}