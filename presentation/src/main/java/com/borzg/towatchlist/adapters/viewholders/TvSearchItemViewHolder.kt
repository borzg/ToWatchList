package com.borzg.towatchlist.adapters.viewholders

import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.adapters.OnListItemClickListener
import com.borzg.towatchlist.databinding.LiTvSearchBinding
import com.borzg.towatchlist.ui.search.TvItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl

class TvSearchItemViewHolder(val binding: LiTvSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item : SearchResult, onListItemClickListener: OnListItemClickListener<SearchResult>) {
        val viewModel = TvItemViewModel(item as TvSearchResult)
        with(binding) {
            tvTitle.text = viewModel.title
            releaseDate.text = "2:00"
            loadImageFromUrl(viewModel.posterPath, poster)
            root.setOnClickListener {
                onListItemClickListener.onItemClick(item)
            }
        }
    }
}