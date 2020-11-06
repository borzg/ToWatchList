package com.borzg.towatchlist.adapters.viewholders

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.cardview.widget.CardView
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.databinding.LiMovieSearchBinding
import com.borzg.towatchlist.ui.search.MovieSearchItemViewModel
import com.borzg.towatchlist.utils.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class MovieSearchItemViewHolder(val binding: LiMovieSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item: SearchResult, onListItemClickListener: (SearchResult, CardView) -> Unit) {
        val viewModel = MovieSearchItemViewModel(item as MovieSearchResult)
        with(binding) {
            movieTitle.text = viewModel.title
            releaseDate.text = viewModel.runtime
            posterCard.transitionName = viewModel.posterPath
            loadImageFromUrl(viewModel.posterPath, poster)
            loadImageToCache(item.backdrop_path, poster.context)
            root.setOnClickListener {
                onListItemClickListener.invoke(item, posterCard)
            }
        }
    }
}