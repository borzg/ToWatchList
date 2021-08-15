package com.borzg.towatchlist.ui.detail.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borzg.domain.model.Movie
import com.borzg.domain.service.DetailMovieService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailMovieService: DetailMovieService
) : ViewModel() {

    init {

        Log.d("TAG", "creating detail vm")
    }
    private var isMovieInitialized = false

    lateinit var movie: StateFlow<Movie?>

    // Doing so because of hilt assisted injection implementation problems
    fun setupMovie(movieId: Int) {
        if (!isMovieInitialized) {
            isMovieInitialized = true
            movie = detailMovieService.getMovieDetails(movieId)
                .flowOn(Dispatchers.IO)
                .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
        }
    }

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            detailMovieService.addMovieToWatchList(movie)
        }
    }

}