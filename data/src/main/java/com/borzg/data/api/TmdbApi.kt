package com.borzg.data.api

import com.borzg.data.api.model.MovieNetwork
import com.borzg.data.api.model.TmdbResponse
import com.borzg.data.api.model.search.MovieSearchResultNetwork
import com.borzg.data.api.model.search.SearchResultNetwork
import com.borzg.data.api.model.TvNetwork
import retrofit2.http.*

interface TmdbApi {

    @GET("search/multi")
    suspend fun getMultiSearchResult(
        @Query("query") query: String,
        @Query("page") pageNumber: Int = 1
    ): TmdbResponse<SearchResultNetwork>

    @GET("movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId: Int): MovieNetwork

    @GET("tv/{tv_id}")
    suspend fun getTv(@Path("tv_id") tvId: Int): TvNetwork

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") pageNumber: Int = 1
    ): TmdbResponse<MovieSearchResultNetwork>
}
