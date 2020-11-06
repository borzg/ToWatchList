package com.borzg.towatchlist.adapters.viewholders

import androidx.cardview.widget.CardView
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.search.TvSearchResult
import com.borzg.towatchlist.databinding.LiTvSearchBinding
import com.borzg.towatchlist.ui.search.TvSearchItemViewModel
import com.borzg.towatchlist.utils.loadImageFromUrl

class TvSearchItemViewHolder(val binding: LiTvSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item: SearchResult, onListItemClickListener: (SearchResult, CardView) -> Unit) {
        val viewModel = TvSearchItemViewModel(item as TvSearchResult)
        with(binding) {
            tvTitle.text = viewModel.title
            firstAirDate.text = viewModel.firstAirDate
            posterCard.transitionName = viewModel.posterPath
            loadImageFromUrl(viewModel.posterPath, poster)
            root.setOnClickListener {
                onListItemClickListener.invoke(item, posterCard)
            }
        }
    }
}