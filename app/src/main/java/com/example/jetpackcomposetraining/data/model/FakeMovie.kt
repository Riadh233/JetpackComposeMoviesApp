package com.example.jetpackcomposetraining.data.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.example.jetpackcomposetraining.data.network.Credits

data class FakeMovie(
    val id : Int,
    val title : String,
    val description : String,
    val imgUrl : Int,
    val rating : Float,
    val views : String,
    val director : String,
    val actors : List<String>
){
    fun doesMatchSearchQuery(query : String) : Boolean{
        val matchingCombination = listOf(
            title
        )
        return matchingCombination.any {
            it.contains(query , ignoreCase = true)
        }
    }
}

