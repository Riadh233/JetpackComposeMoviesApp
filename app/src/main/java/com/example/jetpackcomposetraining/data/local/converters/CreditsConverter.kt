package com.example.jetpackcomposetraining.data.local.converters

import androidx.room.TypeConverter
import com.example.jetpackcomposetraining.data.network.Cast
import com.example.jetpackcomposetraining.data.network.Credits
import com.example.jetpackcomposetraining.data.network.Crew
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class CreditsConverter {
    @TypeConverter
    fun fromString(value: String): Credits {
        val type = Types.newParameterizedType(Credits::class.java, List::class.java, Cast::class.java, Crew::class.java)
        val adapter: JsonAdapter<Credits> = Moshi.Builder().build().adapter(type)
        return adapter.fromJson(value) ?: Credits()
    }

    @TypeConverter
    fun toString(value: Credits): String {
        val type = Types.newParameterizedType(Credits::class.java, List::class.java, Cast::class.java, Crew::class.java)
        val adapter: JsonAdapter<Credits> = Moshi.Builder().build().adapter(type)
        return adapter.toJson(value)
    }
}