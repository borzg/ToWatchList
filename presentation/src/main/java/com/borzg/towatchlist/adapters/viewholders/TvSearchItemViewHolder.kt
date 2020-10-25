package com.borzg.towatchlist.adapters.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.R
import com.borzg.towatchlist.adapters.OnSearchItemClickListener
import com.borzg.towatchlist.databinding.LiMovieBinding
import com.borzg.towatchlist.databinding.LiTvBinding
import com.borzg.towatchlist.ui.search.MovieItemViewModel
import com.borzg.towatchlist.ui.search.TvItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl

class TvSearchItemViewHolder(val binding: LiTvBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item : SearchResult, onSearchItemClickListener: OnSearchItemClickListener) {
        val viewModel = TvItemViewModel(item as TvSearchResult, onSearchItemClickListener)
        with(binding) {
            tvTitle.text = viewModel.title
            releaseDate.text = "2:00"
            loadImageFromUrl(viewModel.posterPath, poster)
            root.setOnClickListener {
                onSearchItemClickListener.onItemClick(item)
            }
        }
    }
}