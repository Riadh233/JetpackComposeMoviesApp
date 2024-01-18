package com.example.jetpackcomposetraining.data.model

import android.os.Parcelable
import com.example.jetpackcomposetraining.data.network.Cast
import com.example.jetpackcomposetraining.data.network.Crew
import com.example.jetpackcomposetraining.data.network.Genre
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Movie(
    val id: Long,
    val imageUrl: String?,
    val title : String,
    val overview : String,
    val rating : Float,
    val popularity : Float,
    val isPopular : Boolean = false,
    val genreList : List<Int>,
    val cast : List<@RawValue Cast>,
    val crew : List<@RawValue Crew>
) : Parcelable