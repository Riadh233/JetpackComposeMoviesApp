package com.example.jetpackcomposetraining.util

enum class MovieGenres(val genre: String) {
    Action("Action"),
    Adventure("Adventure"),
    Comedy("Comedy"),
    Crime("Crime"),
    Documentary("Documentary"),
    Drama("Drama"),
    Family("Family"),
    Fantasy("Fantasy"),
    Horror("Horror"),
    Musical("Musical"),
    Mystery("Mystery"),
    Romance("Romance"),
    ScienceFiction("Science Fiction"),
    Thriller("Thriller")

}

fun getAllMovieGenres(): List<String> {
    return listOf(
        "Action",
        "Adventure",
        "Comedy",
        "Crime" ,
        "Documentary" ,
        "Drama" ,
        "Family" ,
        "Fantasy",
        "Horror",
        "Musical",
        "Mystery",
        "Romance" ,
        "Science Fiction",
        "Thriller",
    )

}
fun getMovieGenre(genre : String) : MovieGenres?{
    val map = MovieGenres.values().associateBy(MovieGenres::genre)
    return map[genre]
}
