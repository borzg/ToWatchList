package com.borzg.towatchlist.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.adapters.OnListItemClickListener

abstract class WatchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(cinemaElement: CinemaElement, onListItemClickListener: OnListItemClickListener<CinemaElement>)
}