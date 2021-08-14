package com.borzg.towatchlist.ui.detail.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borzg.domain.model.Tv
import com.borzg.domain.service.DetailTvService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val detailTvService: DetailTvService
) : ViewModel() {

    private var isTvInitialized = false

    lateinit var tv: Flow<Tv>

    // Doing so because of hilt assisted injection implementation problems
    fun setupTv(tvId: Int) {
        if (!isTvInitialized) {
            isTvInitialized = true
            tv = detailTvService.getTvDetails(tvId).flowOn(Dispatchers.IO)
        }
    }

    fun addTvToWatchList(tv: Tv) {
        viewModelScope.launch(Dispatchers.IO) { detailTvService.addTvToWatchList(tv) }
    }
}