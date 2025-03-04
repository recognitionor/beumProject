package com.kal.beum.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Update or Insert
    @Upsert
    suspend fun upsert(book: AppEntity)

    // getList
    @Query("SELECT * FROM AppEntity")
    fun getFavoriteBooks(): Flow<List<AppEntity>>

    // getTarget
    @Query("SELECT * FROM AppEntity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): AppEntity?

    // delete
    @Query("DELETE FROM AppEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)
}