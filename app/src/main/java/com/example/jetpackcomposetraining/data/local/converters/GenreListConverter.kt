package com.example.jetpackcomposetraining.data.local.converters

import androidx.room.TypeConverter

class GenreListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}