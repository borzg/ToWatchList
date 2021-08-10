package com.borzg.domain.service

import com.borzg.domain.model.Movie
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.model.search.SearchResult
import kotlinx.coroutines.flow.Flow

interface DetailMovieService {

    fun getMovieDetails(movieId: Int): Flow<Movie>

    fun getSimilarMovies(movieId: Int): Flow<List<MovieSearchResult>>

    suspend fun addMovieToWatchList(movie: Movie)
}