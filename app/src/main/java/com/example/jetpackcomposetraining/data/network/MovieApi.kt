package com.example.jetpackcomposetraining.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage : Int,
        @Query("with_genres") selectedGenre : String,
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
        @Query("query") query: String,
        @Query("api_key") apiKey : String
    ): MovieDtoPage

    @GET("movie/{id}")
    suspend fun getMovieWithCredits(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "credits"
    ): MovieDto

}