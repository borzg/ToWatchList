package com.borzg.domain.repository

import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.flow.Flow

interface AdditionalCinemaRepository {

    fun getSimilarMovies(movieId: Int, pageNumber: Int = 1): Flow<List<SearchResult.MovieSearchResult>>
}