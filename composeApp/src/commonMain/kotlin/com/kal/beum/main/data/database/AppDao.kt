package com.kal.beum.main.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnBoardingStatus(appEntity: AppEntity)

    @Query("SELECT isOnBoardingDone FROM AppEntity WHERE id = 1 LIMIT 1")
    suspend fun getOnBoardingStatus(): Boolean
}
