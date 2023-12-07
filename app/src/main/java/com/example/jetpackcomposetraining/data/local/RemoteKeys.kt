package com.example.jetpackcomposetraining.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpackcomposetraining.util.Constants.REMOTE_KEYS_ENTITY

@Entity(tableName = REMOTE_KEYS_ENTITY)
data class RemoteKeys(
    @PrimaryKey val id: Long,
    val prevPage: Int?,
    val nextPage: Int?
)