package com.borzg.data.service.implementations

import android.util.Log
import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.data.service.DetailMovieService
import com.borzg.domain.model.Movie
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor() : DetailMovieService {

    @Inject
    @Server
    lateinit var detailServerCinemaRepository: DetailCinemaRepository

    @Inject
    @DB
    lateinit var detailDBCinemaRepository: DetailCinemaRepository

    override suspend fun addMovieToWatchList(movie: Movie) {
        detailDBCinemaRepository.insertMovie(movie)
    }

    override fun getMovieDetails(movieId: Int): Flow<Movie> {
        return detailServerCinemaRepository.getMovie(movieId).combine(detailDBCinemaRepository.getMovie(movieId)) { movieFromServer, movieFromDb ->
            val resultMovie = movieFromServer!!
            if (movieFromDb != null) {
                resultMovie.addTime = movieFromDb.addTime
                resultMovie.isViewed = movieFromDb.isViewed
                if (resultMovie != movieFromDb) detailDBCinemaRepository.updateMovie(resultMovie)
            }
            resultMovie
        }
    }

}