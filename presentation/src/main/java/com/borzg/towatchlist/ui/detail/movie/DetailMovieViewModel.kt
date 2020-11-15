package com.borzg.towatchlist.ui.detail.movie

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.borzg.domain.service.DetailMovieService
import com.borzg.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailMovieViewModel @ViewModelInject constructor(
    private val detailMovieService: DetailMovieService,
    @Assisted private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    fun getMovieDetails(movieId: Int): LiveData<Movie> =
        detailMovieService.getMovieDetails(movieId).flowOn(Dispatchers.IO).asLiveData()

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            detailMovieService.addMovieToWatchList(movie)
        }
    }

}