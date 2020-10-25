package com.borzg.data.service.implementations

import com.borzg.domain.model.DB
import com.borzg.domain.model.Server
import com.borzg.domain.repository.DetailCinemaRepository
import com.borzg.data.service.DetailMovieService
import com.borzg.domain.model.Movie
import io.reactivex.Single
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor() : DetailMovieService {

    @Inject @Server
    lateinit var detailServerCinemaRepository: DetailCinemaRepository
    @Inject @DB
    lateinit var detailDBCinemaRepository: DetailCinemaRepository

    override fun getElement(elementId: Int): Single<Movie> = detailServerCinemaRepository.getMovie(elementId)
            .doOnSuccess {
                //TODO Ошибки в переведении сложных типов в json
                println("Success")
                detailDBCinemaRepository.insertMovie(it)
            }
            .onErrorReturn {
                println("Error message: " + it.message)
                //TODO сделать проверку на ошибки сервера (ApiUtils)
                detailDBCinemaRepository.getMovie(elementId).blockingGet()
            }


}