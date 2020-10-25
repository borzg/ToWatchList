package com.borzg.towatchlist.adapters

import com.borzg.domain.model.search.SearchResult

fun interface OnSearchItemClickListener {
    fun onItemClick(item: SearchResult)
}