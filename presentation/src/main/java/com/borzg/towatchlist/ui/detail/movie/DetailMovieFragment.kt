package com.borzg.towatchlist.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.borzg.towatchlist.R
import com.borzg.towatchlist.databinding.FrMovieDetailBinding
import com.borzg.towatchlist.utils.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private val args: DetailMovieFragmentArgs by navArgs()
    lateinit var binding: FrMovieDetailBinding
    var posterArray: ByteArray? = null

    val transitionDuration = 400L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        transition.interpolator = DecelerateInterpolator()
        transition.duration = transitionDuration
        sharedElementEnterTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrMovieDetailBinding.inflate(inflater)
        with(binding) {
            posterCard.transitionName = args.posterUrl
            loadImageFromUrl(args.posterUrl, poster)

            loadImageFromUrlWithBlur(args.backdropPath, backgroundImage)
            title.text = args.title
            if (args.title != args.originalTitle) originalTitle.text = args.originalTitle
            else originalTitle.visibility = View.GONE
            releaseDate.text = args.releaseDate
        }
        return binding.root
    }

    enum class MovieState {
        ADDED_ON_START,
        NOT_ADDED_ON_START,
        UNKNOWN
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var movieState = MovieState.UNKNOWN
        viewModel.getMovieDetails(args.id).observe(viewLifecycleOwner, { movie ->
            with(binding) {
//                movie.poster = posterArray
                if (movie.isDisplayed == true && movieState == MovieState.UNKNOWN) {
                    movieState = MovieState.ADDED_ON_START
                    fab.visibility = View.GONE
                } else if (movieState == MovieState.UNKNOWN) movieState =
                    MovieState.NOT_ADDED_ON_START

                if (movie.isDisplayed == true && movieState == MovieState.NOT_ADDED_ON_START) hideFab()
                else fab.setOnClickListener {
                    viewModel.addMovieToWatchList(movie)
                    Toast.makeText(
                        activity,
                        getString(R.string.added_to_watchList),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })
    }

    private fun hideFab() {
        with(binding.fab) {
            val params = layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior as FloatingActionButton.Behavior
            behavior.isAutoHideEnabled = false
            hide()
        }
    }

}