package com.example.jetpackcomposetraining.data.local.converters

import androidx.room.TypeConverter
import com.example.jetpackcomposetraining.data.network.Crew
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CrewTypeConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromString(value: String?): List<Crew>? {
        if (value == null) return emptyList()

        val type = Types.newParameterizedType(List::class.java, Crew::class.java)
        val adapter = moshi.adapter<List<Crew>>(type)

        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun toString(value: List<Crew>?): String {
        val type = Types.newParameterizedType(List::class.java, Crew::class.java)
        val adapter = moshi.adapter<List<Crew>>(type)

        return adapter.toJson(value ?: emptyList())
    }
}