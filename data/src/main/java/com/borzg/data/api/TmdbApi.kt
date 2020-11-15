package com.borzg.data.api

import com.borzg.domain.model.Movie
import com.borzg.domain.model.Person
import com.borzg.domain.model.common.TmdbResponse
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.tv.Tv
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*


interface TmdbApi {

    @GET("search/multi")
    suspend fun getMultiSearchResult(@Query("query") query: String, @Query("page") pageNumber: Int = 1) : TmdbResponse<SearchResult>

    @GET("movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId : Int) : Movie

    @GET("tv/{tv_id}")
    suspend fun getTv(@Path("tv_id") tvId : Int) : Tv

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int, @Query("page") pageNumber: Int = 1) : TmdbResponse<SearchResult.MovieSearchResult>
}
