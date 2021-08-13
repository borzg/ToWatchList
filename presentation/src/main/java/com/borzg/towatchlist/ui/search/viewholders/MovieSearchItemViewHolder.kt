package com.borzg.towatchlist.ui.search.viewholders

import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.databinding.LiMovieSearchBinding
import com.borzg.towatchlist.ui.search.MovieSearchItemViewModel
import com.borzg.towatchlist.utils.*

class MovieSearchItemViewHolder(private val binding: LiMovieSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item: SearchResult, onListItemClickListener: (SearchResult) -> Unit) {
        val vm = MovieSearchItemViewModel(item as MovieSearchResult)
        with(binding) {
            movieTitle.text = vm.title
            releaseDate.text = vm.releaseDate
            if (vm.originalTitle != vm.title)
                originalTitle.setTextAndVisibility(vm.originalTitle)
            else originalTitle.hideView()
            poster.loadImageFromUrl(vm.posterPath)
            loadImageToCache(item.backdropPath, poster.context)
            root.setOnClickListener {
                onListItemClickListener.invoke(item)
            }
        }
    }
}