package com.borzg.towatchlist.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.adapters.OnSearchItemClickListener

abstract class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: SearchResult, onSearchItemClickListener: OnSearchItemClickListener)
}