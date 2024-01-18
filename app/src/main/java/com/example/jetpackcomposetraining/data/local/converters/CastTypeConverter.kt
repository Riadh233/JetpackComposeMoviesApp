package com.example.jetpackcomposetraining.data.local.converters

import androidx.room.TypeConverter
import com.example.jetpackcomposetraining.data.network.Cast
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CastTypeConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromString(value: String?): List<Cast>? {
        if (value == null) return emptyList()

        val type = Types.newParameterizedType(List::class.java, Cast::class.java)
        val adapter = moshi.adapter<List<Cast>>(type)

        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun toString(value: List<Cast>?): String {
        val type = Types.newParameterizedType(List::class.java, Cast::class.java)
        val adapter = moshi.adapter<List<Cast>>(type)

        return adapter.toJson(value ?: emptyList())
    }
}