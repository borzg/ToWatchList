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
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.search.SearchResult
import com.borzg.towatchlist.R
import com.borzg.towatchlist.adapters.CinemaSearchAdapter
import com.borzg.towatchlist.databinding.FrSearchBinding
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.showView
import com.borzg.towatchlist.utils.simpleStartAlphaAnimation
import com.borzg.towatchlist.utils.startAlphaAnimationIfHidden
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.IndexOutOfBoundsException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.reflect.KProperty

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var searchJob: Job? = null
    private lateinit var binding: FrSearchBinding
    private lateinit var searchListLoadStates: Flow<CombinedLoadStates>

    private val viewModel: SearchViewModel by viewModels()
    private var currentQuery
        get() = viewModel.currentQuery
        set(value) {
            viewModel.currentQuery = value
        }

    private val adapter: CinemaSearchAdapter = CinemaSearchAdapter { searchResult ->
        val action: NavDirections = when (searchResult) {
            is SearchResult.MovieSearchResult -> SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(
                searchResult.id,
                searchResult.title,
                searchResult.original_title,
                searchResult.posterPath ?: "",
                searchResult.release_date ?: "",
                searchResult.backdrop_path ?: ""
            )
            is SearchResult.TvSearchResult -> SearchFragmentDirections.actionSearchFragmentToDetailTvFragment(
                searchResult.id,
                searchResult.name,
                searchResult.originalName,
                searchResult.posterPath ?: "",
                searchResult.firstAirDate ?: "",
                "",
                searchResult.backdrop_path ?: "",
                false
            )
            SearchResult.DummySearchResult -> throw IllegalStateException("Invalid DummySearchResult type in search results list")
        }
        findNavController().navigate(action)
    }

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentQuery.isBlank()) showDefaultLayout()
        else search(currentQuery)
    }

    override fun onStop() {
        super.onStop()
        searchJob?.cancel()
    }

    private fun bindSwipeRefreshLayout() {
        binding.swipeRefresher.setOnRefreshListener {
            search(currentQuery)
        }
    }

    private fun bindSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO Add suggestions
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
        searchListLoadStates = adapter.loadStateFlow
        lifecycleScope.launch(Dispatchers.Main) {
            // Drop first default emit to set default layout
            // Debounce is here for handling only last state when loadStates emitting very fast (faster then 10 milliseconds)
            searchListLoadStates.drop(1).debounce(10).collect {
                handleLoadStates(it)
            }
        }
    }

    @MainThread
    private fun handleLoadStates(loadStates: CombinedLoadStates) {
        val state = loadStates.refresh
        setRefreshState(state is LoadState.Loading)
        if (state is LoadState.Loading) return
        if (state is LoadState.Error) {
            when (state.error) {
                is UnknownHostException -> showNoInternetConnectionLayout()
                is SSLHandshakeException -> showToastMessage(getString(R.string.date_time_error))
            }
        } else if (state is LoadState.NotLoading && loadStates.append.endOfPaginationReached && binding.searchList.adapter?.itemCount == 0)
            showEmptyListLayout()
        else showSearchList()
    }

    @MainThread
    private fun setRefreshState(isRefreshing: Boolean) {
        binding.swipeRefresher.isRefreshing = isRefreshing
    }

    @MainThread
    private fun showToastMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSearchList() {
        with(binding) {
            searchList.showView()
            errorLayout.hideView()
        }
    }

    private fun showDefaultLayout() {
        with(binding) {
            errorTv.text = getString(R.string.no_search_text)
            searchList.hideView()
            errorLayout.startAlphaAnimationIfHidden(300)
        }
    }

    private fun showEmptyListLayout() {
        with(binding) {
            errorTv.text = getString(R.string.nothing_found)
            searchList.hideView()
            errorLayout.startAlphaAnimationIfHidden(300)
        }
    }

    private fun showNoInternetConnectionLayout() {
        with(binding) {
            errorTv.text = getString(R.string.probably_no_internet_connection)
            searchList.hideView()
            errorLayout.startAlphaAnimationIfHidden(300)
        }
    }

    fun search(query: String?) {
        if (!query.isNullOrBlank()) {
            currentQuery = query
            searchJob?.cancel()
            searchJob = lifecycleScope.launch(Dispatchers.IO) {
                try {
                    viewModel.searchData(query).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                } catch (e: Throwable) {
                    Log.e("TAG", "search: ${e.stackTrace}", e)
                }
            }
        } else {
            setRefreshState(false)
            showToastMessage(getString(R.string.search_string_is_empty))
        }
    }
}