package com.borzg.towatchlist.ui.search.viewholders

import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.databinding.LiTvSearchBinding
import com.borzg.towatchlist.ui.search.TvSearchItemViewModel
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.loadImageFromUrl
import com.borzg.towatchlist.utils.setTextAndVisibility

class TvSearchItemViewHolder(private val binding: LiTvSearchBinding) :
    SearchViewHolder(binding.root) {

    override fun bind(item: SearchResult, onListItemClickListener: (SearchResult) -> Unit) {
        val vm = TvSearchItemViewModel(item as SearchResult.TvSearchResult)
        with(binding) {
            tvName.text = vm.name
            if (vm.originalName != vm.name)
                originalName.setTextAndVisibility(vm.originalName)
            else originalName.hideView()
            firstAirDate.setTextAndVisibility(vm.firstAirDate)
            loadImageFromUrl(vm.posterPath, poster)
            root.setOnClickListener {
                onListItemClickListener.invoke(item)
            }
        }
    }
}