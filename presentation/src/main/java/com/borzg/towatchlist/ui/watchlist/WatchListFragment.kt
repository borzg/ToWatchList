package com.borzg.towatchlist.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.borzg.domain.model.Movie
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.adapters.WatchListAdapter
import com.borzg.towatchlist.databinding.FrWatchlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var binding: FrWatchlistBinding

    private val adapter = WatchListAdapter(
        { cinemaElement ->

        },
        { cinemaElement, isWatched ->
            viewModel.setWatchedState(isWatched, cinemaElement)
        })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrWatchlistBinding.inflate(inflater)
        bindWatchList()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getContentFromWatchList().observe(viewLifecycleOwner, { list ->
            adapter.submitList(list)
        })
    }

    private fun bindWatchList() {
        with(binding.watchList) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@WatchListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

}