package com.example.jetpackcomposetraining.data.network

import com.squareup.moshi.Json

data class MovieDtoPage(
    @Json(name="page") val page : Int,
    @Json(name = "results") val results : List<MovieDto>,
    @Json(name = "total_pages") val totalPages : Int
)
