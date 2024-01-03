package com.example.jetpackcomposetraining.data.local.converters

import androidx.room.TypeConverter
import com.example.jetpackcomposetraining.data.network.Cast
import com.example.jetpackcomposetraining.data.network.Credits
import com.example.jetpackcomposetraining.data.network.Crew
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CreditsConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun creditsToJson(credits: Credits?): String? {
        return credits?.let {
            val type = Types.newParameterizedType(Credits::class.java)
            val adapter = moshi.adapter<Credits>(type)
            adapter.toJson(credits)
        }
    }

    @TypeConverter
    fun jsonToCredits(json: String?): Credits? {
        return json?.let {
            val type = Types.newParameterizedType(Credits::class.java)
            val adapter = moshi.adapter<Credits>(type)
            adapter.fromJson(json)
        }
    }
}
