package com.example.jetpackcomposetraining.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.data.network.Credits
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
    var isPopular : Boolean = false
)

fun MovieEntity.toDomainModel() : Movie {
    return Movie(
        id = id,
        imageUrl = imageUrl,
        title = title,
        overview = overview,
        rating = rating,
        popularity = popularity,
    )
}
