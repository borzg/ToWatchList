package com.borzg.domain.service.implementations

import com.borzg.domain.DB
import com.borzg.domain.Server
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.domain.model.Movie
import com.borzg.domain.model.search.MovieSearchResult
import com.borzg.domain.repository.AdditionalCinemaRepository
import com.borzg.domain.service.DetailMovieService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor(
    private val additionalCinemaRepository: AdditionalCinemaRepository,
    @param:DB private val detailDBCinemaRepository: DetailCinemaRepository,
    @param:Server private val detailServerCinemaRepository: DetailCinemaRepository
) : DetailMovieService {

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
                val newMovie: Movie = movie.copyCinemaElementParametersFrom(movieStateKeeper!!)
                movieStateKeeper = newMovie
                detailDBCinemaRepository.updateMovie(newMovie)
            }
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<List<MovieSearchResult>> =
        additionalCinemaRepository.getSimilarMovies(movieId)

    override suspend fun addMovieToWatchList(movie: Movie) {
        detailDBCinemaRepository.addMovieToWatchList(movie)
    }

}