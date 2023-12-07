package com.example.jetpackcomposetraining.data.network

import androidx.room.Embedded
import com.squareup.moshi.Json


data class Credits(
    @Json(name = "cast")
    val cast : List<Cast> = emptyList(),
    @Json(name = "crew")
    val crew : List<Crew> = emptyList()
)

data class Cast(
    @Json(name = "name") val name: String? = null,
    @Json(name = "profile_path") val profileImage: String? = null
)

data class Crew(
    @Json(name = "name") val name: String? = null,
    @Json(name = "job") val job: String? = null
)
