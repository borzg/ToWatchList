package com.borzg.towatchlist.ui.detail.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.borzg.data.service.DetailMovieService
import com.borzg.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailMovieViewModel @ViewModelInject constructor(private val detailMovieService: DetailMovieService) :
    ViewModel() {

    fun getMovieDetails(movieId: Int): LiveData<Movie> =
        detailMovieService.getMovieDetails(movieId).asLiveData()

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            detailMovieService.addMovieToWatchList(movie)
        }
    }
}