package com.kal.beum.main.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppEntity(appEntity: AppEntity)

    @Query("SELECT isOnBoardingDone FROM AppEntity WHERE id = 1 LIMIT 1")
    suspend fun getOnBoardingStatus(): Boolean

    @Query("UPDATE AppEntity SET isDevil = :isDevil WHERE id = :id")
    suspend fun updateIsDevil(id: Int = 1, isDevil: Boolean)

    @Query("SELECT * FROM AppEntity WHERE id = 1 LIMIT 1")
    suspend fun getAppEntity(): AppEntity?

    @Query("SELECT isDevil FROM AppEntity WHERE id = 1 LIMIT 1")
    suspend fun isDevil(): Boolean

    @Query("SELECT * FROM UserInfoEntity WHERE id = 1 LIMIT 1")
    suspend fun getLoginInfo(): UserInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLoginInfo(userInfoEntity: UserInfoEntity)
}
