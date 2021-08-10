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
    private val detailMovieService: DetailMovieService,
) : ViewModel() {

    fun getMovieDetails(movieId: Int): Flow<Movie> =
        detailMovieService.getMovieDetails(movieId).flowOn(Dispatchers.IO)

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            detailMovieService.addMovieToWatchList(movie)
        }
    }

}