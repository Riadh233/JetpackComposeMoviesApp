package com.example.jetpackcomposetraining.data.network

import com.example.jetpackcomposetraining.data.local.MovieEntity
import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id") val id: Long,
    @Json(name = "poster_path") val imageUrl: String? = null,
    @Json(name = "title") val title : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "vote_average") val rating : Float,
    @Json(name = "popularity") val popularity : Float,
)

fun MovieDto.toMovieEntity(timestamp : Long) : MovieEntity {
    return MovieEntity(
        id = id,
        imageUrl = imageUrl,
        title = title,
        overview = overview,
        rating = rating,
        timestamp = timestamp,
        popularity = popularity,
    )
}
