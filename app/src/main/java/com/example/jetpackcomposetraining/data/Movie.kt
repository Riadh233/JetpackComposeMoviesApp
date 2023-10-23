package com.example.jetpackcomposetraining.data

data class Movie(
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
