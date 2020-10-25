package com.borzg.towatchlist.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.borzg.towatchlist.adapters.CinemaSearchAdapter
import com.borzg.towatchlist.adapters.OnSearchItemClickListener
import com.borzg.towatchlist.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var searchJob: Job? = null
    private lateinit var binding: SearchFragmentBinding

    private val viewModel: SearchViewModel by viewModels()
    private val onSearchItemClickListener = OnSearchItemClickListener {
        Log.d("TAG", ": ${it}")
    }
    private val adapter: CinemaSearchAdapter = CinemaSearchAdapter(onSearchItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(LayoutInflater.from(activity))
        bindSearchList()
        bindSwipeRefreshLayout()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        search("harry potter")
    }

    private fun bindSwipeRefreshLayout() {
        binding.swipeRefresher.setOnRefreshListener {
            search("big bang")
        }
        adapter.addLoadStateListener {
            setRefreshState(it.source.refresh is LoadState.Loading)
        }
        viewModel.isDataTheSame.observe(viewLifecycleOwner, { isDataTheSame ->
            if (isDataTheSame) setRefreshState(false)
        })
    }

    private fun bindSearchList() {
        with(binding.searchList) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@SearchFragment.adapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun setRefreshState(isRefreshing: Boolean) {
        binding.swipeRefresher.isRefreshing = isRefreshing
    }

    fun search(query: String?) {
        if (!query.isNullOrEmpty()) {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.searchData(query).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}