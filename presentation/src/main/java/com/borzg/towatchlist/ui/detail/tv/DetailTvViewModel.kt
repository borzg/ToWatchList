package com.borzg.towatchlist.ui.detail.tv

import dagger.assisted.Assisted
import androidx.lifecycle.*
import com.borzg.domain.service.DetailTvService
import com.borzg.domain.model.tv.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val service: DetailTvService
) : ViewModel() {

    fun getTvDetails(tvId: Int): LiveData<Tv> =
        service.getTvDetails(tvId).asLiveData()

    fun addTvToWatchList(tv: Tv) {
        viewModelScope.launch(Dispatchers.IO) { service.addTvToWatchList(tv) }
    }
}