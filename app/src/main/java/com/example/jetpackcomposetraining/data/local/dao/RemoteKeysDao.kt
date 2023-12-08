package com.example.jetpackcomposetraining.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposetraining.data.local.RemoteKeys


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys : List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id : Long) : RemoteKeys

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()

}