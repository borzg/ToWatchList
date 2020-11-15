package com.borzg.towatchlist.ui.detail.tv

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.borzg.domain.service.DetailTvService
import com.borzg.domain.model.tv.Tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTvViewModel @ViewModelInject constructor(
    val service: DetailTvService,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getTvDetails(tvId: Int): LiveData<Tv> =
        service.getTvDetails(tvId).asLiveData()

    fun addTvToWatchList(tv: Tv) {
        viewModelScope.launch(Dispatchers.IO) { service.addTvToWatchList(tv) }
    }
}