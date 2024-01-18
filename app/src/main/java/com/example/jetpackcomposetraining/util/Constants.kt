package com.example.jetpackcomposetraining.util

object Constants {
    const val DATABASE_NAME = "movies_database"
    const val MOVIES_ENTITY = "movies_table"
    const val REMOTE_KEYS_ENTITY = "remote_keys"
    const val ITEMS_PER_PAGE = 20
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val MOVIE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
    const val ITEMS_CONTENT_TYPE = "Movies Items"
    const val ALL_MOVIES = 0

    private val genresMap = mapOf(
        0 to "All",
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )

    fun getGenreId(genre: String): Int {
        return genresMap.entries.find { it.value == genre }?.key ?: 0
    }

    fun getAllGenres(): List<String> {
        return genresMap.values.toList()
    }

}