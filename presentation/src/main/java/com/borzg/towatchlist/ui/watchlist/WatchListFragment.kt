package com.borzg.towatchlist.ui.watchlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.borzg.domain.model.Movie
import com.borzg.domain.model.tv.Tv
import com.borzg.towatchlist.adapters.SwipeToDeleteCallback
import com.borzg.towatchlist.adapters.WatchListAdapter
import com.borzg.towatchlist.databinding.FrWatchlistBinding
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.showView
import com.borzg.towatchlist.utils.simpleStartAlphaAnimation
import com.borzg.towatchlist.utils.startAlphaAnimationIfHidden
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var binding: FrWatchlistBinding

    private val adapter = WatchListAdapter(
        { cinemaElement ->
            val action = when (cinemaElement) {
                is Movie -> WatchListFragmentDirections.actionWatchListFragmentToDetailMovieFragment(
                    cinemaElement.id,
                    cinemaElement.title,
                    cinemaElement.original_title,
                    cinemaElement.posterPath ?: "",
                    cinemaElement.releaseDate,
                    cinemaElement.backdrop_path ?: ""
                )
                is Tv -> WatchListFragmentDirections.actionWatchListFragmentToDetailTvFragment(
                    cinemaElement.id,
                    cinemaElement.name,
                    cinemaElement.originalName,
                    cinemaElement.posterPath ?: "",
                    cinemaElement.firstAirDate ?: "",
                    cinemaElement.lastAirDate ?: "",
                    cinemaElement.backdropPath ?: "",
                    cinemaElement.inProduction
                )
                else -> throw IllegalStateException("Invalid type of CinemaElement")
            }
            findNavController().navigate(action)
        },
        { cinemaElement, isWatched ->
            viewModel.setWatchedState(isWatched, cinemaElement)
        })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrWatchlistBinding.inflate(inflater)
        bindActionBar()
        bindWatchList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            forAllTimeTv.simpleStartAlphaAnimation()
            forWeekTv.simpleStartAlphaAnimation()
            forMonthTv.simpleStartAlphaAnimation()
        }
    }

    var currentListSize = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getContentFromWatchList().observe(viewLifecycleOwner, { list ->
            if (list.isEmpty()) showEmptyWatchListLayout()
            else {
                showWatchListLayout()
                adapter.submitList(list) {
                    if (currentListSize != list.size) binding.watchList.layoutManager?.scrollToPosition(0)
                    currentListSize = list.size
                }
            }
        })
    }

    private fun showWatchListLayout() {
        with(binding) {
            emptyListLayout.hideView()
            watchList.showView()
            appBarLayout.showView()
        }
    }

    private fun showEmptyWatchListLayout() {
        with(binding) {
            watchList.hideView()
            appBarLayout.hideView()
            emptyListLayout.startAlphaAnimationIfHidden(400)
        }
    }

    private fun bindActionBar() {
        with(binding) {
            viewModel.numberOfViewsForAllTime().observe(viewLifecycleOwner, {
                viewsForAllTime.animateSetForAllTimesViews(it)
            })
            viewModel.numberOfViewsForWeek().observe(viewLifecycleOwner, {
                viewsForWeek.animateSetNumber(it)
            })
            viewModel.numberOfViewsForMonth().observe(viewLifecycleOwner, {
                viewsForMonth.animateSetNumber(it)
            })
        }
    }

    private val decelerateInterpolator = DecelerateInterpolator()

    private fun TextView.animateSetForAllTimesViews(number: Int) {
        if (text == number.toString()) return
        val anim = animate().scaleX(0.5f).scaleY(0.5f).alpha(0f).withEndAction {
            text = number.toString()
            animate().scaleX(1f).scaleY(1f).alpha(1f).start()
        }
        anim.interpolator = decelerateInterpolator
        anim.start()
    }

    private fun TextView.animateSetNumber(number: Int) {
        if (text == number.toString()) return
        val anim = animate().alpha(0f).withEndAction {
            text = number.toString()
            animate().alpha(1f).start()
        }
        anim.interpolator = decelerateInterpolator
        anim.start()
    }

    private fun bindWatchList() {
        with(binding.watchList) {
//            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@WatchListFragment.adapter
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback {
                viewModel.removeFromWatchList(it)
            })
            itemTouchHelper.attachToRecyclerView(this)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

}