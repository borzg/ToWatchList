package com.borzg.towatchlist.adapters.viewholders

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.borzg.towatchlist.adapters.OnSearchItemClickListener
import com.borzg.towatchlist.databinding.LiMovieBinding
import com.borzg.towatchlist.ui.search.MovieItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl
import com.bumptech.glide.Glide

class MovieSearchItemViewHolder(val binding: LiMovieBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item : SearchResult, onSearchItemClickListener: OnSearchItemClickListener) {
        val viewModel = MovieItemViewModel(item as MovieSearchResult, onSearchItemClickListener)
        with(binding) {
            movieTitle.text = viewModel.title
            releaseDate.text = "2:00"
            loadImageFromUrl(viewModel.posterPath, poster)
            root.setOnClickListener {
                onSearchItemClickListener.onItemClick(item)
            }
        }
    }
}