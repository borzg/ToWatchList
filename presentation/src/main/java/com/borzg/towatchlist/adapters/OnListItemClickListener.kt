package com.borzg.towatchlist.adapters

import com.borzg.domain.model.search.SearchResult

fun interface OnListItemClickListener<T : Any> {
    fun onItemClick(item: T)
}