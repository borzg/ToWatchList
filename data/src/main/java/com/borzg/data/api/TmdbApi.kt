package com.borzg.data.api

import com.borzg.domain.model.Movie
import com.borzg.domain.model.Person
import com.borzg.domain.model.common.TmdbResponse
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.model.tv.Tv
import io.reactivex.Single
import retrofit2.http.*


interface TmdbApi {

//    @GET("authentication/token/new")
//    fun getNewAuthToken() : Single<TokenRequest>
//
//    @POST("authentication/session/new")
//    @FormUrlEncoded
//    fun getSessionId(@Field("request_token") requestToken : String) : Single<SessionRequest>
//
//
//    @GET("search/multi")
//    suspend fun getMultiSearchResult(@Query("query") query: String, @Query("page") pageNumber: Int) : TmdbResponse<CinemaSearchResult>

    @GET("search/multi")
    suspend fun getMultiSearchResult(@Query("query") query: String, @Query("page") pageNumber: Int = 1) : TmdbResponse<SearchResult>

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId : Int) : Single<Movie>

    @GET("person/{person_id}")
    fun getPerson(@Path("person_id") personId : Int) : Single<Person>

    @GET("tv/{tv_id}")
    fun getTv(@Path("tv_id") tvId : Int) : Single<Tv>
}
