package com.example.jetpackcomposetraining.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage : Int,
        @Query("api_key") apiKey : String,
    ): MovieDtoPage

    @GET("movie/top_rated")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage : Int,
        @Query("api_key") apiKey : String
    ): MovieDtoPage

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String,
        @Query("api_key") apiKey : String
    ): MovieDtoPage

}