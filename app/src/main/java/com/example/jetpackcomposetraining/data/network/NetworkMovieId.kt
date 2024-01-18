package com.example.jetpackcomposetraining.data.network

import com.squareup.moshi.Json

data class NetworkMovieId(
    @Json(name = "id") val id: Long,
)
