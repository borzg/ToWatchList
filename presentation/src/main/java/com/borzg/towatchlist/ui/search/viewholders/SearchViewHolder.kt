package com.borzg.towatchlist.ui.search.viewholders

import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.search.SearchResult

abstract class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: SearchResult, onListItemClickListener: (SearchResult) -> Unit)
}