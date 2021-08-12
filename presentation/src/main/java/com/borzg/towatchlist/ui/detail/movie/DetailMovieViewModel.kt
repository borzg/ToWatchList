package com.borzg.towatchlist.ui.detail.movie

import androidx.lifecycle.*
import com.borzg.domain.service.DetailMovieService
import com.borzg.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailMovieService: DetailMovieService
) : ViewModel() {

    private var isMovieInitialized = false

    lateinit var movie: Flow<Movie>

    // Doing so because of hilt assisted injection implementation problems
    fun setupMovie(movieId: Int) {
        if (!isMovieInitialized) {
            isMovieInitialized = true
            movie = detailMovieService.getMovieDetails(movieId).flowOn(Dispatchers.IO)
        }
    }

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            detailMovieService.addMovieToWatchList(movie)
        }
    }

}