package com.borzg.towatchlist.ui.detail.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.borzg.domain.model.Movie
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.FrDetailMovieBinding
import com.borzg.towatchlist.ui.detail.DetailCinemaElementFragment
import com.borzg.towatchlist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieFragment : DetailCinemaElementFragment() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private val args: DetailMovieFragmentArgs by navArgs()
    private var _binding: FrDetailMovieBinding? = null
    private val binding: FrDetailMovieBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMovieDetails(args.id).collect { movie ->
                    with(binding) {
                        fab.setStateDependingOnCinemaElementState(movie)
                        fab.setOnClickListener {
                            viewModel.addMovieToWatchList(movie)
                            Toast.makeText(
                                activity,
                                getString(R.string.added_to_watchList),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        bindDetailsLayout(movie)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrDetailMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNothing()
        with(binding) {
            poster.loadImageFromUrl(args.posterUrl)
            posterCard.animatePosterAppearance()
            loadImageFromUrlWithBlur(args.backdropPath, backgroundImage)
            title.text = args.title
            if (args.title != args.originalTitle) originalTitle.text = args.originalTitle
            else originalTitle.hideView()
            releaseDate.text = args.releaseDate.getYearFromDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initializeBinders() {
        bindRatingLayout()
        bindOverviewLayout()
        bindBudgetLayout()
        bindToImdbButton()
    }

    override fun showNothing() {
        with(binding) {
            movieDetailsLayout.hideView()
            nothingToShowLayout.root.showView()
        }
    }

    override fun showDetailLayout() {
        with(binding) {
            nothingToShowLayout.root.hideView()
            movieDetailsLayout.startAlphaAnimationIfHidden(400)
        }
    }

    private fun bindBudgetLayout() {
        addBinderForLayout(binding.budgetCard) {
            this as Movie
            with(binding) {
                if (budget == 0L || revenue == 0L) {
                    return@with false
                }
                budgetTv.text = budget.formatToUsDollars()
                revenueTv.text = revenue.formatToUsDollars()
                return@with true
            }
        }
    }

    private fun bindRatingLayout() {
        addBinderForLayout(binding.ratingCard) {
            this as Movie
            with(binding) {
                if (vote_average != 0f && vote_count != 0) {
                    ratingBarWithNumber.ratingBar.rating = vote_average
                    ratingBarWithNumber.ratingNumberTv.text = vote_average.toString()
                    ratingBarWithNumber.ratingNumberTv.setTextColorForRating(vote_average)
                    voteCount.text =
                        getString(R.string.vote_count_with_number, vote_count)
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun bindOverviewLayout() {
        addBinderForLayout(binding.movieOverviewCard) {
            this as Movie
            with(binding) {
                if (overview.isNullOrBlank()) {
                    return@with false
                }
                overviewTv.text = overview
                return@with true
            }
        }
    }

    private fun bindToImdbButton() {
        addBinderForLayout(binding.toImdbBtn) {
            this as Movie
            with(binding.toImdbBtn) {
                if (imdbId == null) {
                    return@with false
                }
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(BuildConfig.IMDB_ID_URL + imdbId)
                    startActivity(intent)
                }
                return@with true
            }
        }
    }

}