package com.borzg.towatchlist.ui.detail.tv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.borzg.domain.model.Movie
import com.borzg.domain.model.tv.Tv
import com.borzg.towatchlist.BuildConfig
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.FrDetailTvBinding
import com.borzg.towatchlist.ui.detail.DetailCinemaElementFragment
import com.borzg.towatchlist.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvFragment : DetailCinemaElementFragment() {

    private lateinit var binding: FrDetailTvBinding
    private val viewModel: DetailTvViewModel by viewModels()
    private val args: DetailTvFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrDetailTvBinding.inflate(inflater)
        with(binding) {
            poster.loadImageFromUrl(args.posterUrl)
            posterCard.animatePosterAppearance()
            loadImageFromUrlWithBlur(args.backdropPath, backgroundImage)
            name.text = args.name
            if (args.name != args.originalName) originalName.text = args.originalName
            else originalName.hideView()
            if (!args.lastAirDate.isBlank()) airDates.text = formatYearsPeriod(
                args.firstAirDate.getYearFromDate(),
                args.lastAirDate,
                args.inProduction
            )
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showNothing()
        viewModel.getTvDetails(args.id).observe(viewLifecycleOwner, { tv ->
            with(binding) {
                fab.setStateDependingOnCinemaElementState(tv)
                fab.setOnClickListener {
                    viewModel.addTvToWatchList(tv)
                    Toast.makeText(
                        activity,
                        getString(R.string.added_to_watchList),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                airDates.text = formatYearsPeriod(tv.firstAirDate, tv.lastAirDate, tv.inProduction)
                bindDetailsLayout(tv)
            }
        })
    }

    override fun initializeBinders() {
        bindOverviewLayout()
        bindRatingLayout()
        bindSeasonsLayout()
    }

    override fun showNothing() {
        with(binding) {
            tvDetailsLayout.hideView()
            nothingToShowLayout.root.showView()
        }
    }

    override fun showDetailLayout() {
        with(binding) {
            nothingToShowLayout.root.hideView()
            tvDetailsLayout.simpleStartAlphaAnimation()
        }
    }

    private fun bindRatingLayout() {
        addBinderForLayout(binding.ratingCard) {
            this as Tv
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
        addBinderForLayout(binding.overviewCard) {
            this as Tv
            with(binding) {
                if (overview.isNullOrBlank()) {
                    return@with false
                }
                overviewTv.text = overview
                return@with true
            }
        }
    }

    private fun bindSeasonsLayout() {
        addBinderForLayout(binding.seasonsCard) {
            this as Tv
            with(binding) {
                if (numberOfSeasons == null || episode_run_time == null || episode_run_time!!.isEmpty()) return@with false
                seasonsCountTv.text = numberOfSeasons.toString()
                episodeRunTimeTv.text = getString(
                    R.string.episode_runtime,
                    episode_run_time?.get(0)?.minutesToTimeFormat()
                )
                return@with true
            }
        }
    }

}