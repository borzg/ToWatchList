package com.borzg.towatchlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.R
import com.borzg.towatchlist.adapters.viewholders.MovieSearchItemViewHolder
import com.borzg.towatchlist.adapters.viewholders.SearchViewHolder
import com.borzg.towatchlist.adapters.viewholders.TvSearchItemViewHolder
import com.borzg.towatchlist.databinding.LiMovieBinding
import com.borzg.towatchlist.databinding.LiTvBinding

const val MOVIE_TYPE = 1
const val TV_TYPE = 2

class CinemaSearchAdapter(private val onItemClickListener : OnSearchItemClickListener) : PagingDataAdapter<SearchResult, SearchViewHolder>(SearchItemDiffCallback) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MovieSearchResult -> MOVIE_TYPE
            is TvSearchResult -> TV_TYPE
            else -> throw IllegalStateException("Type of searchresult is not yet implemented in function getItemViewType()")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            MOVIE_TYPE -> return MovieSearchItemViewHolder(LiMovieBinding.inflate(inflater, parent, false))
            TV_TYPE -> return TvSearchItemViewHolder(LiTvBinding.inflate(inflater, parent, false))
        }
        throw java.lang.IllegalStateException("Wrong viewType")
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (getItem(position) != null) holder.bind(getItem(position)!!, onItemClickListener)
    }

    object SearchItemDiffCallback : DiffUtil.ItemCallback<SearchResult>() {

        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            if (!oldItem.media_type.equals(newItem.media_type)) return false
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            if (!oldItem.media_type.equals(newItem.media_type)) return false
            return oldItem == newItem
        }

    }


}