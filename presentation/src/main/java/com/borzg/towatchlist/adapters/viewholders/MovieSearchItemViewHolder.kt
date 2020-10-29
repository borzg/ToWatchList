package com.borzg.towatchlist.adapters.viewholders

import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.adapters.OnListItemClickListener
import com.borzg.towatchlist.databinding.LiMovieSearchBinding
import com.borzg.towatchlist.ui.search.MovieItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl
import com.borzg.towatchlist.utils.loadImageToCache

class MovieSearchItemViewHolder(val binding: LiMovieSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item : SearchResult, onListItemClickListener: OnListItemClickListener<SearchResult>) {
        val viewModel = MovieItemViewModel(item as MovieSearchResult)
        with(binding) {
            movieTitle.text = viewModel.title
            releaseDate.text = "2:00"
            loadImageFromUrl(viewModel.posterPath, poster)
            loadImageToCache(item.backdrop_path, poster.context)
            root.setOnClickListener {
                onListItemClickListener.onItemClick(item)
            }
        }
    }
}