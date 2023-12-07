package com.example.jetpackcomposetraining.data.model

import com.example.jetpackcomposetraining.data.network.Credits

data class Movie(
    val id: Long,
    val imageUrl: String?,
    val title : String,
    val overview : String,
    val rating : Float,
    val popularity : Float,
    val isPopular : Boolean = false
)