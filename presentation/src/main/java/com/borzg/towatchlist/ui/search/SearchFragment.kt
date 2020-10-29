package com.borzg.towatchlist.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.search.DummySearchResult
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.towatchlist.adapters.CinemaSearchAdapter
import com.borzg.towatchlist.adapters.OnSearchItemClickListener
import com.borzg.towatchlist.databinding.FrSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var searchJob: Job? = null
    private lateinit var binding: FrSearchBinding
    private var currentQuery = ""

    private val viewModel: SearchViewModel by viewModels()
    private val onSearchItemClickListener = OnSearchItemClickListener {
        //TODO Разделить на TVsearchResult и MovieSearchResult
        it as MovieSearchResult
        val action = SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(
            it.id,
            it.title,
            it.original_title,
            it.posterPath ?: "",
            it.release_date,
            it.backdrop_path ?: ""
        )
        findNavController().navigate(action)
    }
    private val adapter: CinemaSearchAdapter = CinemaSearchAdapter(onSearchItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrSearchBinding.inflate(LayoutInflater.from(activity))
        bindSearchList()
        bindSwipeRefreshLayout()
        bindSearchView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        search("harry potter")
    }

    private fun bindSwipeRefreshLayout() {
        binding.swipeRefresher.setOnRefreshListener {
            search(currentQuery)
        }
        adapter.addLoadStateListener {
            setRefreshState(it.source.refresh is LoadState.Loading)
        }
    }

    private fun bindSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Do nothing
                return false
            }

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

    private fun showEmptyListText() {
        with(binding) {
            searchList.visibility = View.GONE
            emptyListLayout.visibility = View.VISIBLE
        }
    }

    @MainThread
    private fun showErrorMessage(message: String?) {
        Toast.makeText(activity, message ?: "Error!", Toast.LENGTH_SHORT).show()
    }

    private fun showSearchList() {
        with(binding) {
            searchList.visibility = View.VISIBLE
            emptyListLayout.visibility = View.GONE
        }
    }

    fun search(query: String?) {
        if (!query.isNullOrEmpty()) {
            currentQuery = query
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.searchData(query).collectLatest { pagingData ->
                    launch(Dispatchers.Main) {
                        adapter.loadStateFlow.collectLatest { loadStates ->
                            // TODO внимательно рассмотреть лоадстейты
                            binding.swipeRefresher.isRefreshing = loadStates.refresh is LoadState.Loading
                            val state = loadStates.source.refresh
                            if (state is LoadState.Error) {
                                showErrorMessage(state.error.message)
                                viewModel.isError = true
                            }
                            Log.d("TAG", "search: $loadStates")
                            if (loadStates.source.append is LoadState.NotLoading && adapter.itemCount == 0)
                                showEmptyListText()
                            else showSearchList()
                        }
                    }
                    adapter.submitData(pagingData.filter {
                        it !is DummySearchResult
                    })
                }
            }
        }
    }
}