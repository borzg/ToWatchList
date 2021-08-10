package com.borzg.domain.repository

import com.borzg.domain.model.search.MovieSearchResult
import kotlinx.coroutines.flow.Flow

interface AdditionalCinemaRepository {

    fun getSimilarMovies(movieId: Int, pageNumber: Int = 1): Flow<List<MovieSearchResult>>
}