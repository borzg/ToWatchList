package com.borzg.towatchlist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.Movie
import com.borzg.domain.model.CinemaElement
import com.borzg.domain.model.Tv
import com.borzg.towatchlist.databinding.LiMovieWatchlistBinding
import com.borzg.towatchlist.databinding.LiTvWatchlistBinding
import com.borzg.towatchlist.ui.watchlist.viewholders.MovieWatchListViewHolder
import com.borzg.towatchlist.ui.watchlist.viewholders.TvWatchListViewHolder
import com.borzg.towatchlist.ui.watchlist.viewholders.WatchListViewHolder

class WatchListAdapter(
    private val onListItemClickListener: (CinemaElement) -> Unit,
    private val onAddButtonClickListener: suspend (CinemaElement, Boolean) -> Unit
) : ListAdapter<CinemaElement, WatchListViewHolder>(WatchListItemDiffCallback) {

    private companion object {
        const val MOVIE_TYPE = 1
        const val TV_TYPE = 2
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Movie -> MOVIE_TYPE
            is Tv -> TV_TYPE
            else -> throw IllegalStateException("Type of cinemaElement is not yet implemented in function getItemViewType()")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            MOVIE_TYPE -> return MovieWatchListViewHolder(
                LiMovieWatchlistBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            TV_TYPE -> return TvWatchListViewHolder(
                LiTvWatchlistBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
        throw java.lang.IllegalStateException("Wrong viewType")
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(getItem(position), onListItemClickListener, onAddButtonClickListener)
    }

    object WatchListItemDiffCallback : DiffUtil.ItemCallback<CinemaElement>() {

        override fun areItemsTheSame(oldItem: CinemaElement, newItem: CinemaElement): Boolean {
            return oldItem.id == newItem.id && ((oldItem is Movie && newItem is Movie) || (oldItem is Tv && newItem is Tv))
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CinemaElement, newItem: CinemaElement): Boolean {
            return oldItem == newItem
        }

    }
}

class SwipeToDeleteCallback(private val deletingBlock: (CinemaElement) -> Unit): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder as WatchListViewHolder
        viewHolder.onSwipeListener = deletingBlock
        viewHolder.invokeSwipe()
    }

}