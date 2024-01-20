package com.example.jetpackcomposetraining.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.data.network.Cast
import com.example.jetpackcomposetraining.data.network.Crew
import com.example.jetpackcomposetraining.util.Constants.MOVIES_ENTITY


@Entity(tableName = MOVIES_ENTITY)
data class MovieEntity(
    @PrimaryKey val id: Long,
    val imageUrl: String?,
    val title : String,
    val overview : String,
    val rating : Float,
    val popularity : Float,
    val timestamp : Long,
    var listType : Int = 0,
//    val genreList : List<Int>,
    val cast : List<Cast>,
    val crew : List<Crew>
)

fun MovieEntity.toDomainModel() : Movie {
    return Movie(
        id = id,
        imageUrl = imageUrl,
        title = title,
        overview = overview,
        rating = rating,
        popularity = popularity,
//        genreList = genreList,
        cast = cast,
        crew = crew
    )
}
