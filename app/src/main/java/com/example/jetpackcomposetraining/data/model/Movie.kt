package com.example.jetpackcomposetraining.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val imageUrl: String?,
    val title : String,
    val overview : String,
    val rating : Float,
    val popularity : Float,
    val isPopular : Boolean = false
) : Parcelable