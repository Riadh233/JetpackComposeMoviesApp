package com.example.jetpackcomposetraining.data.network

import com.example.jetpackcomposetraining.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage : Int,
        @Query("api_key") apiKey : String
    ): MovieDtoPage

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage : Int,
        @Query("api_key") apiKey : String
    ): MovieDtoPage

    @GET("search/movie")
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieDtoPage

}