package com.borzg.domain.service.implementations

import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.model.Movie
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.AdditionalCinemaRepository
import com.borzg.domain.service.DetailMovieService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor() : DetailMovieService {

    @Inject
    @Server
    lateinit var detailServerCinemaRepository: DetailCinemaRepository

    @Inject
    @DB
    lateinit var detailDBCinemaRepository: DetailCinemaRepository

    @Inject
    lateinit var additionalCinemaRepository: AdditionalCinemaRepository

    var movieStateKeeper: Movie? = null
        @Synchronized get
        @Synchronized set

    @FlowPreview
    override fun getMovieDetails(movieId: Int): Flow<Movie> {
        movieStateKeeper = null
        return flowOf(
            detailDBCinemaRepository.getMovie(movieId),
            detailServerCinemaRepository.getMovie(movieId)
                .catch { println("getMovieDetails from server: $it ${it.message}") }
        ).flattenMerge().onEach { movie ->
            // On first iteration or if element comes from DB
            if (movieStateKeeper == null || movie.addTime != null) {
                movieStateKeeper = movie
            } else {
                // When element comes from server
                movie.copyCinemaElementParametersFrom(movieStateKeeper!!)
                movieStateKeeper = movie
                detailDBCinemaRepository.updateMovie(movie)
            }
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<List<SearchResult.MovieSearchResult>> =
        additionalCinemaRepository.getSimilarMovies(movieId)

    override suspend fun addMovieToWatchList(movie: Movie) {
        detailDBCinemaRepository.addMovieToWatchList(movie)
    }

}